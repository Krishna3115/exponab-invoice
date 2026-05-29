package com.exponab.invoice.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.PurchaseOrder;
import com.exponab.invoice.pdf.PdfLayoutBuilder;
import com.exponab.invoice.repo.PurchaseOrderRepo;

import jakarta.mail.internet.MimeMessage;

@Service
public class PurchaseOrderEmailService {

    private final JavaMailSender mailSender;
    private final PurchaseOrderRepo purchaseOrderRepo;

    public PurchaseOrderEmailService(JavaMailSender mailSender,
                                     PurchaseOrderRepo purchaseOrderRepo) {
        this.mailSender = mailSender;
        this.purchaseOrderRepo = purchaseOrderRepo;
    }

    public void sendPurchaseOrder(Long poId) {
        try {
            PurchaseOrder po = purchaseOrderRepo.findById(poId)
                    .orElseThrow(() -> new RuntimeException("PO not found"));

            Company exporter = po.getExporter();
            if (exporter == null || exporter.getEmail() == null || exporter.getEmail().isBlank()) {
                throw new RuntimeException("Exporter email not found");
            }

            byte[] pdf = PdfLayoutBuilder.buildPurchaseOrder(po);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(exporter.getEmail());
            helper.setSubject("Purchase Order - " + po.getPoNumber());
            helper.setText("""
                    Dear Supplier,

                    Please find attached our Purchase Order.

                    Regards,
                    Exponab General Trading LLC
                    """);
            helper.addAttachment("purchase-order.pdf", new ByteArrayResource(pdf));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email failed: " + e.getMessage());
        }
    }
}