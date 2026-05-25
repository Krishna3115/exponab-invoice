package com.exponab.invoice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exponab.invoice.constants.QuotationConstants;
import com.exponab.invoice.dto.request.QuotationRequestDto;
import com.exponab.invoice.dto.request.QuotationSendRequestDto;
import com.exponab.invoice.dto.response.QuotationItemResponseDto;
import com.exponab.invoice.dto.response.QuotationResponseDto;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.entity.Quotation;
import com.exponab.invoice.entity.QuotationItem;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.QuotationRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DocumentGenerationService documentService;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public QuotationResponseDto createQuotation(QuotationRequestDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + dto.getCompanyId()));

        Quotation quotation = new Quotation();
        quotation.setCompany(company);

        quotation.setDiscountPercent(dto.getDiscountPercent() != null ? dto.getDiscountPercent()
                : QuotationConstants.DEFAULT_DISCOUNT_PERCENT);
        quotation.setTaxPercent(dto.getTaxPercent() != null ? dto.getTaxPercent()
                : QuotationConstants.DEFAULT_TAX_PERCENT);

        List<QuotationItem> items = dto.getItems().stream().map(itemDTO -> {
            QuotationItem item = new QuotationItem();
            item.setQuotation(quotation);
            item.setServiceDescription(itemDTO.getServiceDescription());
            item.setPrice(itemDTO.getPrice());
            return item;
        }).collect(Collectors.toList());

        quotation.setItems(items);

        BigDecimal subtotal = items.stream()
                .map(QuotationItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = subtotal.multiply(quotation.getDiscountPercent())
                .divide(BigDecimal.valueOf(100));
        BigDecimal afterDiscount = subtotal.subtract(discountAmount);
        BigDecimal taxAmount = afterDiscount.multiply(quotation.getTaxPercent())
                .divide(BigDecimal.valueOf(100));

        quotation.setSubtotal(subtotal);
        quotation.setDiscountAmount(discountAmount);
        quotation.setTaxAmount(taxAmount);
        quotation.setTotalAmount(afterDiscount.add(taxAmount));

        Quotation savedQuotation = quotationRepository.save(quotation);
        return mapToResponseDto(savedQuotation);
    }

    @Transactional
    public void generateAndSendQuotation(Long quotationId, QuotationSendRequestDto emailRequest) {
        // 1. Fetch Quotation
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new RuntimeException("Quotation not found with id: " + quotationId));

        Company company = quotation.getCompany();

        // 2. Map to DTO so the DocumentService can read it easily
        QuotationResponseDto quotationDto = mapToResponseDto(quotation);

        try {
            // 3. Generate PDF Bytes (Using the method I provided in the previous step)
            byte[] pdfBytes = documentService.generateQuotationPdf(quotationDto);

            // 4. Setup Email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(company.getEmail());
            helper.setSubject(emailRequest.getEmailSubject());
            helper.setText(emailRequest.getEmailBody());

            // 5. Attach PDF
            ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("Quotation_" + company.getCompanyName().replaceAll("\\s+", "_") + ".pdf",
                    pdfAttachment);

            // 6. Send Email
            mailSender.send(message);

            // 7. Update Status
          //  company.updateStatus(CompanyStatus.QUOTATION_SENT);

        } catch (Exception e) {
            System.err.println("Failed to generate and send quotation for company " + company.getId());
            e.printStackTrace();
            throw new RuntimeException("Failed to send quotation email", e);
        }
    }

    public List<QuotationResponseDto> getAllQuotations() {
        return quotationRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public QuotationResponseDto getQuotationById(String id) {
        Quotation quotation = quotationRepository.findByQuotationNumber(id)
                .orElseThrow(() -> new RuntimeException("Quotation not found with id: " + id));
        return mapToResponseDto(quotation);
    }

    public List<QuotationResponseDto> getQuotationsByCompany(Long companyId) {
        return quotationRepository.findByCompanyId(companyId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteQuotation(Long id) {
        quotationRepository.deleteById(id);
    }

    // --- HELPER MAPPER METHOD ---
    private QuotationResponseDto mapToResponseDto(Quotation quotation) {
        QuotationResponseDto dto = new QuotationResponseDto();
        dto.setId(quotation.getId());
        dto.setQuotationNumber(quotation.getQuotationNumber());

        if (quotation.getCompany() != null) {
            dto.setCompanyId(quotation.getCompany().getId());
            dto.setCompanyName(quotation.getCompany().getCompanyName());
        }

        dto.setDiscountPercent(quotation.getDiscountPercent());
        dto.setTaxPercent(quotation.getTaxPercent());
        dto.setSubtotal(quotation.getSubtotal());
        dto.setDiscountAmount(quotation.getDiscountAmount());
        dto.setTaxAmount(quotation.getTaxAmount());
        dto.setTotalAmount(quotation.getTotalAmount());
        dto.setCreatedAt(quotation.getCreatedAt());

        if (quotation.getItems() != null) {
            List<QuotationItemResponseDto> itemDtos = quotation.getItems().stream()
                    .map(item -> new QuotationItemResponseDto(item.getId(), item.getServiceDescription(),
                            item.getPrice()))
                    .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }
        return dto;
    }
}