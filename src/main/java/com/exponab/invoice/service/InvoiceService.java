package com.exponab.invoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.InvoiceRequestDto;
import com.exponab.invoice.dto.request.InvoiceSendRequestDto;
import com.exponab.invoice.dto.response.InvoiceItemResponseDto;
import com.exponab.invoice.dto.response.InvoiceResponseDto;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.entity.Invoice;
import com.exponab.invoice.entity.InvoiceItem;
import com.exponab.invoice.entity.Quotation;
import com.exponab.invoice.repo.InvoiceRepository;
import com.exponab.invoice.repo.QuotationRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private QuotationRepository quotationRepo;
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private DocumentGenerationService documentService;
    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public InvoiceResponseDto createInvoiceFromQuotation(InvoiceRequestDto dto) {
        Quotation quotation = quotationRepo.findFirstByCompanyIdOrderByCreatedAtDesc(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("No quotation found for this company"));

        Company company = quotation.getCompany();
        Invoice invoice = new Invoice();
        invoice.setCompany(company);
        invoice.setTaxPercent(dto.getTaxPercent());
        invoice.setDiscountPercent(dto.getDiscountPercent());
        invoice.setDueDate(dto.getDueDate());

        List<InvoiceItem> invoiceItems = quotation.getItems().stream()
                .map(qItem -> new InvoiceItem(qItem.getServiceDescription(), qItem.getPrice(), invoice))
                .collect(Collectors.toList());

        invoice.setItems(invoiceItems);
        invoice.calculateFinancials();

        Invoice savedInvoice = invoiceRepo.save(invoice);
        return mapToResponseDto(savedInvoice);
    }

    @Transactional
    public InvoiceResponseDto removeItemFromInvoice(Long invoiceId, Long itemId) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + invoiceId));

        boolean removed = invoice.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new RuntimeException("Item not found in this invoice");
        }

        invoice.calculateFinancials();
        Invoice updatedInvoice = invoiceRepo.save(invoice);

        // Return the updated DTO so the frontend can refresh the screen immediately
        return mapToResponseDto(updatedInvoice);
    }

    // --- Added standard fetch methods for the API ---
    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceRepo.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public InvoiceResponseDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));
        return mapToResponseDto(invoice);
    }

    // --- HELPER MAPPER METHOD ---
    private InvoiceResponseDto mapToResponseDto(Invoice invoice) {
        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());

        if (invoice.getCompany() != null) {
            dto.setCompanyId(invoice.getCompany().getId());
            dto.setCompanyName(invoice.getCompany().getCompanyName());
        }

        dto.setDiscountPercent(invoice.getDiscountPercent());
        dto.setTaxPercent(invoice.getTaxPercent());
        dto.setSubtotal(invoice.getSubtotal());
        dto.setDiscountAmount(invoice.getDiscountAmount());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setDueDate(invoice.getDueDate());
        dto.setCreatedAt(invoice.getCreatedAt());

        if (invoice.getItems() != null) {
            List<InvoiceItemResponseDto> itemDtos = invoice.getItems().stream()
                    .map(item -> new InvoiceItemResponseDto(item.getId(), item.getServiceDescription(),
                            item.getPrice()))
                    .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }
        return dto;
    }

    @Transactional
    public void generateAndSendInvoice(Long invoiceId, InvoiceSendRequestDto emailRequest) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));

        Company company = invoice.getCompany();
        InvoiceResponseDto invoiceDto = mapToResponseDto(invoice);

        try {
            // 1. Generate PDF Bytes
            byte[] pdfBytes = documentService.generateInvoicePdf(invoiceDto);

            // 2. Setup Email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(company.getEmail());
            helper.setSubject(emailRequest.getEmailSubject());
            helper.setText(emailRequest.getEmailBody());

            // 3. Attach PDF
            ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment(
                    invoice.getInvoiceNumber() + "_" + company.getCompanyName().replaceAll("\\s+", "_") + ".pdf",
                    pdfAttachment);

            // 4. Send Email
            mailSender.send(message);

            // 5. Update Status (Safeguard applied: only advance if they are in
            // AGREEMENT_SENT)
//            if (company.getStatus() == CompanyStatus.AGREEMENT_SIGNED) {
//                company.updateStatus(CompanyStatus.INVOICED);
//            }
//
        } catch (Exception e) {
            System.err.println("Failed to generate and send invoice for company " + company.getId());
            e.printStackTrace();
            throw new RuntimeException("Failed to send invoice email", e);
        }
    }
}