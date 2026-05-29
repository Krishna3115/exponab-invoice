package com.exponab.invoice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.pdf.PdfMergeUtil;
import com.exponab.invoice.pdf.PdfService;
import com.exponab.invoice.repo.SalesReportRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class SalesReportEmailService {

    private final JavaMailSender mailSender;
    private final SalesReportRepository salesReportRepository;
    private final PdfService pdfService;

    public SalesReportEmailService(
            JavaMailSender mailSender,
            SalesReportRepository salesReportRepository,
            PdfService pdfService
    ) {
        this.mailSender = mailSender;
        this.salesReportRepository = salesReportRepository;
        this.pdfService = pdfService;
    }

    // ============================================================
    // ORIGINAL: send report without attachments
    // ============================================================
    public void sendSalesReport(Long reportId) {

        try {
            SalesReport report = salesReportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Sales Report not found"));

            Company customer = report.getCustomer();

            if (customer == null
                    || customer.getEmail() == null
                    || customer.getEmail().isBlank()) {

                throw new RuntimeException("Customer email not found");
            }

            byte[] pdf = pdfService.generateSalesReportPdf(reportId);

            sendEmail(
                    customer.getEmail(),
                    report.getReportNumber(),
                    pdf
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email failed: " + e.getMessage());
        }
    }

    // ============================================================
    // NEW: send report with extra PDF attachments merged in
    // ============================================================
    public void sendSalesReportWithAttachments(
            Long reportId,
            MultipartFile[] attachments) {

        try {
            SalesReport report = salesReportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Sales Report not found"));

            Company customer = report.getCustomer();

            if (customer == null
                    || customer.getEmail() == null
                    || customer.getEmail().isBlank()) {

                throw new RuntimeException("Customer email not found");
            }

            // 1. Generate the main report PDF
            byte[] mainPdf = pdfService.generateSalesReportPdf(reportId);

            // 2. Validate + collect attachment bytes
            List<byte[]> extraPdfs = new ArrayList<>();

            if (attachments != null) {

                for (MultipartFile file : attachments) {

                    if (file == null || file.isEmpty()) continue;

                    String name = file.getOriginalFilename();
                    String contentType = file.getContentType();

                    boolean isPdf =
                            (contentType != null
                                    && contentType.equalsIgnoreCase("application/pdf"))
                            || (name != null
                                    && name.toLowerCase().endsWith(".pdf"));

                    if (!isPdf) {
                        throw new RuntimeException(
                                "Only PDF attachments are allowed: " + name);
                    }

                    extraPdfs.add(file.getBytes());
                }
            }

            // 3. Merge
            byte[] mergedPdf = PdfMergeUtil.merge(mainPdf, extraPdfs);

            // 4. Email
            sendEmail(
                    customer.getEmail(),
                    report.getReportNumber(),
                    mergedPdf
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email failed: " + e.getMessage());
        }
    }

    // ============================================================
    // SHARED: build + send the email
    // ============================================================
    private void sendEmail(String to, String reportNumber, byte[] pdf) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Sales Report - " + reportNumber);
        helper.setText("""
                Dear Customer,

                Please find attached your sales report.

                Regards,
                Exponab General Trading LLC
                """);

        helper.addAttachment(
                "sales-report-" + reportNumber + ".pdf",
                new ByteArrayResource(pdf)
        );

        mailSender.send(message);
    }
}