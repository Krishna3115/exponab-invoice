package com.exponab.invoice.service;

import java.util.Objects;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.SalesReport;
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

    public void sendSalesReport(Long reportId) {

        try {

            SalesReport report =
                    salesReportRepository.findById(reportId)
                    .orElseThrow(() ->
                            new RuntimeException("Sales Report not found"));

            Company customer = report.getCustomer();

            if (customer == null ||
                    customer.getEmail() == null ||
                    customer.getEmail().isBlank()) {

                throw new RuntimeException(
                        "Customer email not found");
            }

            // GENERATE PDF
            byte[] pdf =
                    pdfService.generateSalesReportPdf(reportId);

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );

            helper.setTo(customer.getEmail());

            helper.setSubject(
                    "Sales Report - "
                            + report.getReportNumber()
            );

            helper.setText(
                    """
                    Dear Customer,

                    Please find attached your sales report.

                    Regards,
                    Exponab
                    """
            );

            helper.addAttachment(
                    "sales-report.pdf",
                    new org.springframework.core.io.ByteArrayResource(pdf)
            );

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Email failed: " + e.getMessage()
            );
        }
    }
}