package com.exponab.invoice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.SalesReportItemDto;
import com.exponab.invoice.dto.request.SalesReportRequestDto;
import com.exponab.invoice.dto.response.SalesReportDetailsResponseDto;
import com.exponab.invoice.dto.response.SalesReportExpenseDto;
import com.exponab.invoice.dto.response.SalesReportExpenseResponseDto;
import com.exponab.invoice.dto.response.SalesReportItemResponseDto;
import com.exponab.invoice.dto.response.SalesReportResponseDto;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.PurchaseOrder;
import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.entity.SalesReportExpense;
import com.exponab.invoice.entity.SalesReportItem;
import com.exponab.invoice.entity.TaxMode;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.PurchaseOrderRepo;
import com.exponab.invoice.repo.SalesReportRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalesReportService {

    private final SalesReportRepository repository;
    private final CompanyRepository companyRepository;
    private final PurchaseOrderRepo purchaseOrderRepo;

    public SalesReportService(
            SalesReportRepository repository,
            CompanyRepository companyRepository,
            PurchaseOrderRepo purchaseOrderRepo
    ) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.purchaseOrderRepo = purchaseOrderRepo;
    }

    // =========================================================
    // CREATE
    // =========================================================
    public SalesReportResponseDto createReport(SalesReportRequestDto request) {

        Company customer = companyRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        SalesReport report = new SalesReport();
        report.setReportNumber(generateReportNumber());
        report.setReportDate(LocalDate.now());
        report.setCustomer(customer);
        report.setProcurementType(request.getProcurementType());
        report.setTaxMode(request.getTaxMode());
        report.setNotes(request.getNotes());
        report.setContainerNumber(request.getContainerNumber());

        // OPTIONAL PO
        if (request.getPurchaseOrderId() != null) {
            PurchaseOrder po = purchaseOrderRepo.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PO not found"));
            report.setPurchaseOrder(po);
        }

        // =========================================================
        // ITEMS + VAT CALCULATION
        // =========================================================
        List<SalesReportItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;   // sum of NET (vat-exclusive) line amounts
        BigDecimal totalTax = BigDecimal.ZERO;   // sum of VAT amounts

        for (SalesReportItemDto dto : request.getItems()) {

            SalesReportItem item = new SalesReportItem();
            item.setSalesReport(report);
            item.setArticleName(dto.getArticleName());
            item.setDescription(dto.getDescription());
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(dto.getUnitPrice());

            // Raw line amount (quantity × unit price)
            BigDecimal lineTotal = dto.getQuantity().multiply(dto.getUnitPrice());

            BigDecimal vatPercent =
                    dto.getVatPercent() == null ? BigDecimal.ZERO : dto.getVatPercent();

            BigDecimal vatAmount  = BigDecimal.ZERO;
            BigDecimal baseAmount = lineTotal;
            BigDecimal lineGross  = lineTotal;

            TaxMode mode = request.getTaxMode();

            if (mode == TaxMode.WITHOUT_VAT) {
                // No VAT at all. base = total = lineTotal, vat = 0
                vatAmount  = BigDecimal.ZERO;
                baseAmount = lineTotal;
                lineGross  = lineTotal;
            }
            else if (mode == TaxMode.VAT_INCLUDED) {
                // Business rule: lineTotal IS the inclusive amount.
                // Break it down as base = lineTotal - vatAmount
                // where vatAmount = lineTotal × vatPercent / 100
                // Example: 100 with 5% VAT → vat = 5, base = 95
                vatAmount  = lineTotal.multiply(vatPercent)
                                      .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                baseAmount = lineTotal.subtract(vatAmount);
                lineGross  = lineTotal;   // grand total stays the same as input
            }
            else if (mode == TaxMode.MANUAL_VAT) {
                // VAT added on top of the line amount.
                // Example: 100 base with 5% VAT → vat = 5, total = 105
                vatAmount  = lineTotal.multiply(vatPercent)
                                      .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                baseAmount = lineTotal;
                lineGross  = lineTotal.add(vatAmount);
            }

            item.setVatPercent(vatPercent);
            item.setVatAmount(vatAmount);
            // totalPrice represents the inclusive amount shown in the PDF amount column
            item.setTotalPrice(lineGross);

            subtotal = subtotal.add(baseAmount);
            totalTax = totalTax.add(vatAmount);

            items.add(item);
        }

        report.setItems(items);
        report.setSubtotal(subtotal);
        report.setTaxAmount(totalTax);

        // Grand total = base + VAT (works for all 3 modes since base + vat
        // already equals the inclusive amount in VAT_INCLUDED mode)
        BigDecimal grandTotal = subtotal.add(totalTax);
        report.setGrandTotal(grandTotal);

        // =========================================================
        // EXPENSES — no more ExpenseMaster lookup
        // =========================================================
        List<SalesReportExpense> expenseList = new ArrayList<>();
        BigDecimal totalExpenses = BigDecimal.ZERO;

        if (request.getExpenses() != null) {

            for (SalesReportExpenseDto dto : request.getExpenses()) {

                // Skip null/blank entries silently
                if (dto.getDescription() == null || dto.getDescription().isBlank()) {
                    continue;
                }

                BigDecimal qty  = dto.getQuantity()  == null ? BigDecimal.ZERO : dto.getQuantity();
                BigDecimal rate = dto.getUnitRate()  == null ? BigDecimal.ZERO : dto.getUnitRate();
                BigDecimal amount = qty.multiply(rate);

                SalesReportExpense expense = new SalesReportExpense();
                expense.setSalesReport(report);
                expense.setDescription(dto.getDescription());
                expense.setQuantity(qty);
                expense.setUnitRate(rate);
                expense.setAmount(amount);
                // expenseMaster left null — that's fine, column is nullable

                expenseList.add(expense);
                totalExpenses = totalExpenses.add(amount);
            }
        }

        report.setExpenses(expenseList);
        report.setExpenseTotal(totalExpenses);

        // =========================================================
        // FINAL PAYABLE
        // =========================================================
        BigDecimal finalPayable = grandTotal.subtract(totalExpenses);
        report.setFinalPayable(finalPayable);

        SalesReport saved = repository.save(report);
        return mapToResponse(saved);
    }

    private String generateReportNumber() {
        return "SR-" + System.currentTimeMillis();
    }

    // =========================================================
    // MAP TO RESPONSE — setter-based to survive future field changes
    // =========================================================
    private SalesReportResponseDto mapToResponse(SalesReport report) {

        SalesReportResponseDto dto = new SalesReportResponseDto();
        dto.setId(report.getId());
        dto.setReportNumber(report.getReportNumber());
        dto.setReportDate(report.getReportDate());
        dto.setCustomerName(report.getCustomer().getCompanyName());
        dto.setProcurementType(report.getProcurementType().name());
        dto.setTaxMode(report.getTaxMode().name());
        dto.setSubtotal(report.getSubtotal());
        dto.setTaxAmount(report.getTaxAmount());
        dto.setGrandTotal(report.getGrandTotal());
        dto.setExpenseTotal(report.getExpenseTotal());
        dto.setFinalPayable(report.getFinalPayable());
        dto.setContainerNumber(report.getContainerNumber());
        return dto;
    }

    public SalesReportResponseDto getById(Long id) {
        SalesReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales Report not found"));
        return mapToResponse(report);
    }

    public List<SalesReportResponseDto> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    // =========================================================
    // DETAILS
    // =========================================================
    public SalesReportDetailsResponseDto getReportDetails(Long id) {

        SalesReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales Report not found"));

        SalesReportDetailsResponseDto dto = new SalesReportDetailsResponseDto();
        dto.setId(report.getId());
        dto.setReportNumber(report.getReportNumber());
        dto.setReportDate(report.getReportDate());
        dto.setCustomerId(report.getCustomer().getId());
        dto.setCustomerName(report.getCustomer().getCompanyName());
        dto.setCustomerEmail(report.getCustomer().getEmail());
        dto.setProcurementType(report.getProcurementType().name());
        dto.setTaxMode(report.getTaxMode().name());
        dto.setSubtotal(report.getSubtotal());
        dto.setTaxAmount(report.getTaxAmount());
        dto.setGrandTotal(report.getGrandTotal());
        dto.setNotes(report.getNotes());
        dto.setContainerNumber(report.getContainerNumber());

        if (report.getPurchaseOrder() != null) {
            dto.setPurchaseOrderId(report.getPurchaseOrder().getId());
        }

        // ITEMS
        List<SalesReportItemResponseDto> itemList = new ArrayList<>();
        for (SalesReportItem item : report.getItems()) {
            SalesReportItemResponseDto itemDto = new SalesReportItemResponseDto();
            itemDto.setArticleName(item.getArticleName());
            itemDto.setDescription(item.getDescription());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setVatPercent(item.getVatPercent());
            itemDto.setVatAmount(item.getVatAmount());
            itemDto.setTotalPrice(item.getTotalPrice());
            itemList.add(itemDto);
        }
        dto.setItems(itemList);

        // EXPENSES — null-safe expenseMaster
        List<SalesReportExpenseResponseDto> expenseList = new ArrayList<>();
        for (SalesReportExpense expense : report.getExpenses()) {

            SalesReportExpenseResponseDto expDto = new SalesReportExpenseResponseDto();

            if (expense.getExpenseMaster() != null) {
                expDto.setExpenseMasterId(expense.getExpenseMaster().getId());
                expDto.setExpenseName(expense.getExpenseMaster().getExpenseName());
            } else {
                expDto.setExpenseMasterId(null);
                expDto.setExpenseName(expense.getDescription());
            }

            expDto.setQuantity(expense.getQuantity());
            expDto.setUnitRate(expense.getUnitRate());
            expDto.setAmount(expense.getAmount());
            expenseList.add(expDto);
        }
        dto.setExpenses(expenseList);

        return dto;
    }
}