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
import com.exponab.invoice.entity.ExpenseMaster;
import com.exponab.invoice.entity.PurchaseOrder;
import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.entity.SalesReportExpense;
import com.exponab.invoice.entity.SalesReportItem;
import com.exponab.invoice.entity.TaxMode;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.ExpenseMasterRepository;
import com.exponab.invoice.repo.PurchaseOrderRepo;
import com.exponab.invoice.repo.SalesReportExpenseRepository;
import com.exponab.invoice.repo.SalesReportRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalesReportService {

    private final SalesReportRepository repository;

    private final CompanyRepository companyRepository;

    private final PurchaseOrderRepo purchaseOrderRepo;

    private final ExpenseMasterRepository expenseMasterRepository;

    private final SalesReportExpenseRepository
            salesReportExpenseRepository;

    public SalesReportService(
            SalesReportRepository repository,
            CompanyRepository companyRepository,
            PurchaseOrderRepo purchaseOrderRepo,
            ExpenseMasterRepository expenseMasterRepository,
            SalesReportExpenseRepository salesReportExpenseRepository
    ) {

        this.repository = repository;
        this.companyRepository = companyRepository;
        this.purchaseOrderRepo = purchaseOrderRepo;
        this.expenseMasterRepository =
                expenseMasterRepository;

        this.salesReportExpenseRepository =
                salesReportExpenseRepository;
    }

    public SalesReportResponseDto createReport(
            SalesReportRequestDto request) {

        Company customer = companyRepository.findById(
                request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        SalesReport report = new SalesReport();

        report.setReportNumber(generateReportNumber());

        report.setReportDate(LocalDate.now());

        report.setCustomer(customer);

        report.setProcurementType(
                request.getProcurementType());

        report.setTaxMode(request.getTaxMode());

        report.setNotes(request.getNotes());

        // OPTIONAL PO
        if (request.getPurchaseOrderId() != null) {

            PurchaseOrder po = purchaseOrderRepo.findById(
                    request.getPurchaseOrderId())
                    .orElseThrow(() ->
                            new RuntimeException("PO not found"));

            report.setPurchaseOrder(po);
        }

        // =========================
        // SALES ITEMS
        // =========================

        List<SalesReportItem> items = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        BigDecimal totalTax = BigDecimal.ZERO;

        for (SalesReportItemDto dto : request.getItems()) {

            SalesReportItem item = new SalesReportItem();

            item.setSalesReport(report);

            item.setArticleName(dto.getArticleName());

            item.setDescription(dto.getDescription());

            item.setQuantity(dto.getQuantity());

            item.setUnitPrice(dto.getUnitPrice());

            BigDecimal itemTotal =
                    dto.getQuantity()
                    .multiply(dto.getUnitPrice());

            BigDecimal vatAmount = BigDecimal.ZERO;

            // WITHOUT VAT
            if (request.getTaxMode() == TaxMode.WITHOUT_VAT) {

                vatAmount = BigDecimal.ZERO;
            }

            // VAT INCLUDED
            else if (request.getTaxMode()
                    == TaxMode.VAT_INCLUDED) {

                BigDecimal vatPercent =
                        dto.getVatPercent();

                BigDecimal baseAmount =
                        itemTotal.divide(
                                BigDecimal.ONE.add(
                                        vatPercent.divide(
                                                BigDecimal.valueOf(100),
                                                2,
                                                RoundingMode.HALF_UP)),
                                2,
                                RoundingMode.HALF_UP);

                vatAmount =
                        itemTotal.subtract(baseAmount);
            }

            // MANUAL VAT
            else if (request.getTaxMode()
                    == TaxMode.MANUAL_VAT) {

                vatAmount =
                        itemTotal.multiply(
                                dto.getVatPercent())
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP);
            }

            item.setVatPercent(dto.getVatPercent());

            item.setVatAmount(vatAmount);

            item.setTotalPrice(itemTotal);

            subtotal = subtotal.add(itemTotal);

            totalTax = totalTax.add(vatAmount);

            items.add(item);
        }

        report.setItems(items);

        report.setSubtotal(subtotal);

        report.setTaxAmount(totalTax);

        BigDecimal grandTotal =
                subtotal.add(totalTax);

        report.setGrandTotal(grandTotal);

        // =========================
        // EXPENSES
        // =========================

        List<SalesReportExpense> expenseList
                = new ArrayList<>();

        BigDecimal totalExpenses
                = BigDecimal.ZERO;

        if (request.getExpenses() != null) {

            for (SalesReportExpenseDto dto
                    : request.getExpenses()) {

                ExpenseMaster expenseMaster =
                        expenseMasterRepository
                        .findById(dto.getExpenseMasterId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expense master not found"));

                BigDecimal amount =
                        dto.getQuantity()
                        .multiply(dto.getUnitRate());

                SalesReportExpense expense =
                        new SalesReportExpense();

                expense.setSalesReport(report);

                expense.setExpenseMaster(expenseMaster);

                expense.setDescription(
                        expenseMaster.getExpenseName());

                expense.setQuantity(
                        dto.getQuantity());

                expense.setUnitRate(
                        dto.getUnitRate());

                expense.setAmount(amount);

                expenseList.add(expense);

                totalExpenses =
                        totalExpenses.add(amount);
            }
        }

        report.setExpenses(expenseList);

        report.setExpenseTotal(totalExpenses);

        // =========================
        // FINAL PAYABLE
        // =========================

        BigDecimal finalPayable =
                grandTotal.subtract(totalExpenses);

        report.setFinalPayable(finalPayable);

        // SAVE
        SalesReport saved =
                repository.save(report);

        return mapToResponse(saved);
    }

    private String generateReportNumber() {

        return "SR-" + System.currentTimeMillis();
    }

    private SalesReportResponseDto mapToResponse(
            SalesReport report) {

        return new SalesReportResponseDto(

                report.getId(),

                report.getReportNumber(),

                report.getReportDate(),

                report.getCustomer()
                        .getCompanyName(),

                report.getProcurementType()
                        .name(),

                report.getTaxMode()
                        .name(),

                report.getSubtotal(),

                report.getTaxAmount(),

                report.getGrandTotal(),

                report.getExpenseTotal(),

                report.getFinalPayable()
        );
    }

    public SalesReportResponseDto getById(Long id) {

        SalesReport report = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sales Report not found"));

        return mapToResponse(report);
    }

    public List<SalesReportResponseDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
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

        // EXPENSES
        List<SalesReportExpenseResponseDto> expenseList = new ArrayList<>();

        for (SalesReportExpense expense : report.getExpenses()) {

            SalesReportExpenseResponseDto expDto = new SalesReportExpenseResponseDto();

            expDto.setExpenseMasterId(expense.getExpenseMaster().getId());
            expDto.setExpenseName(expense.getExpenseMaster().getExpenseName());
            expDto.setQuantity(expense.getQuantity());
            expDto.setUnitRate(expense.getUnitRate());
            expDto.setAmount(expense.getAmount());

            expenseList.add(expDto);
        }

        dto.setExpenses(expenseList);

        return dto;
    }
}