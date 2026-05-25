package com.exponab.invoice.service;

import com.exponab.invoice.LetterheadBackgroundHandler;
import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.entity.Clause;
import com.exponab.invoice.repo.ClauseRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;
import com.exponab.invoice.dto.response.QuotationResponseDto;
import com.exponab.invoice.dto.response.InvoiceItemResponseDto;
import com.exponab.invoice.dto.response.InvoiceResponseDto;
import com.exponab.invoice.dto.response.QuotationItemResponseDto;

@Service
public class DocumentGenerationService {

    @Autowired
    private ClauseRepository clauseRepository;

    public byte[] generateCompanyLetterheadPdf(Long companyId) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        ClassPathResource imgResource = new ClassPathResource("letterhead_bg.png");

        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);

            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        Document document = new Document(pdf, PageSize.A4);
        
        document.setMargins(240f, 72f, 120f, 72f);

        List<Clause> clauses = clauseRepository.findByCompanyId(companyId);

        for (Clause clause : clauses) {
            Paragraph p = new Paragraph(clause.getClauseContent())
                    .setFontSize(11f)
                    .setMarginBottom(12f)
                    .setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(p);
        }

        document.close();

        return baos.toByteArray();
    }

    public byte[] generateCustomLetterheadPdf(DocumentGenerationRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        ClassPathResource imgResource = new ClassPathResource("letterhead_bg.png");
        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        Document document = new Document(pdf, PageSize.A4);
        
        document.setMargins(240f, 72f, 120f, 72f);

        if (request.getTemplateContent() != null && !request.getTemplateContent().isEmpty()) {
            String[] templateParagraphs = request.getTemplateContent().split("\n");
            for (String textBlock : templateParagraphs) {
                if (!textBlock.trim().isEmpty()) {
                    Paragraph templatePara = new Paragraph(textBlock)
                            .setFontSize(11f)
                            .setMarginBottom(10f)
                            .setTextAlignment(TextAlignment.JUSTIFIED);
                    document.add(templatePara);
                }
            }
            document.add(new Paragraph("\n"));
        }

        if (request.getSelectedClauseIds() != null && !request.getSelectedClauseIds().isEmpty()) {
            List<Clause> selectedClauses = clauseRepository.findAllById(request.getSelectedClauseIds());

            for (Clause clause : selectedClauses) {
                Paragraph clausePara = new Paragraph(clause.getClauseContent())
                        .setFontSize(11f)
                        .setMarginBottom(12f)
                        .setTextAlignment(TextAlignment.JUSTIFIED);
                document.add(clausePara);
            }
        }

        document.close();
        return baos.toByteArray();
    }

    public byte[] generateProposalPdf(DocumentGenerationRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        ClassPathResource imgResource = new ClassPathResource("letterhead_bg.png");
        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        Document document = new Document(pdf, PageSize.A4);

        document.setMargins(240f, 72f, 120f, 72f);

        if (request.getTemplateContent() != null && !request.getTemplateContent().isEmpty()) {
            String[] templateParagraphs = request.getTemplateContent().split("\n");
            for (String textBlock : templateParagraphs) {
                if (!textBlock.trim().isEmpty()) {
                    Paragraph templatePara = new Paragraph(textBlock)
                            .setFontSize(11f)
                            .setMarginBottom(10f)
                            .setTextAlignment(TextAlignment.JUSTIFIED);
                    document.add(templatePara);
                }
            }
            document.add(new Paragraph("\n"));
        }

        if (request.getSelectedClauseIds() != null && !request.getSelectedClauseIds().isEmpty()) {
            List<Clause> selectedClauses = clauseRepository.findAllById(request.getSelectedClauseIds());

            for (Clause clause : selectedClauses) {
                Paragraph clausePara = new Paragraph(clause.getClauseContent())
                        .setFontSize(11f)
                        .setMarginBottom(12f)
                        .setTextAlignment(TextAlignment.JUSTIFIED);
                document.add(clausePara);
            }
        }

        document.close();
        return baos.toByteArray();
    }

    public byte[] generateAgreementPdf(DocumentGenerationRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        // 1. Pointing to the new A3 background image
        ClassPathResource imgResource = new ClassPathResource("agreement_bg_a3.png");
        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        // 2. Set strict A3 Page Size
        Document document = new Document(pdf, PageSize.A3);

        // 3. Scaled Margins for A3
        // Original A4: 240f (Top), 72f (Right), 120f (Bottom), 72f (Left)
        // Scaled A3: ~340f (Top), 100f (Right), 170f (Bottom), 100f (Left)
        // You may need to tweak the Top/Bottom margin slightly depending on exactly where the header graphic ends on your new image.
        document.setMargins(340f, 100f, 170f, 100f);

        if (request.getTemplateContent() != null && !request.getTemplateContent().isEmpty()) {
            String[] templateParagraphs = request.getTemplateContent().split("\n");
            for (String textBlock : templateParagraphs) {
                if (!textBlock.trim().isEmpty()) {
                    // Increased Font Size (14f) and Spacing (14f) for A3 readability
                    Paragraph templatePara = new Paragraph(textBlock)
                            .setFontSize(14f)
                            .setMarginBottom(14f)
                            .setTextAlignment(TextAlignment.JUSTIFIED);
                    document.add(templatePara);
                }
            }
            document.add(new Paragraph("\n").setFontSize(14f));
        }

        if (request.getSelectedClauseIds() != null && !request.getSelectedClauseIds().isEmpty()) {
            List<Clause> selectedClauses = clauseRepository.findAllById(request.getSelectedClauseIds());

            for (Clause clause : selectedClauses) {
                // Increased Font Size (14f) and Spacing (16f) for A3 readability
                Paragraph clausePara = new Paragraph(clause.getClauseContent())
                        .setFontSize(14f)
                        .setMarginBottom(16f)
                        .setTextAlignment(TextAlignment.JUSTIFIED);
                document.add(clausePara);
            }
        }

        document.close();
        return baos.toByteArray();
    }

    public byte[] generateQuotationPdf(QuotationResponseDto quotation) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        ClassPathResource imgResource = new ClassPathResource("letterhead_bg.png");
        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        Document document = new Document(pdf, PageSize.A4);
        // Margins depend on your letterhead graphic (Top, Right, Bottom, Left)
        document.setMargins(240f, 72f, 120f, 72f);

        // --- 1. PROFESSIONAL HEADER ---
        document.add(new Paragraph("QUOTATION")
                .setBold()
                .setFontSize(18f)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15f));

        // --- 2. META DATA (Bill To, Quote No, Date) ---
        float[] metaWidths = {1, 1};
        Table metaTable = new Table(UnitValue.createPercentArray(metaWidths)).useAllAvailableWidth();
        metaTable.setMarginBottom(25f);

        // Left Side: Bill To
        Cell billToCell = new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
        billToCell.add(new Paragraph("Bill To:").setBold().setFontSize(10f).setFontColor(ColorConstants.GRAY));
        billToCell.add(new Paragraph(quotation.getCompanyName() != null ? quotation.getCompanyName() : "Client Name")
                .setBold().setFontSize(12f));

        // Right Side: Quote Details
        Cell detailsCell = new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);

        String qNumber = quotation.getQuotationNumber() != null ? quotation.getQuotationNumber() : "TBD";
        detailsCell.add(new Paragraph("Quotation No: " + qNumber).setBold().setFontSize(10f));

        // Format the date if it exists
        if (quotation.getCreatedAt() != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy");
            String formattedDate = quotation.getCreatedAt().format(formatter);
            detailsCell.add(new Paragraph("Date: " + formattedDate).setFontSize(10f));
        }

        metaTable.addCell(billToCell);
        metaTable.addCell(detailsCell);
        document.add(metaTable);

        // --- 3. LINE ITEMS TABLE ---
        float[] columnWidths = {1, 7, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
        table.setMarginBottom(20f);

        // Table Headers
        table.addHeaderCell(new Cell().add(new Paragraph("Sr. No.").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Service Description").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount (INR)").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.RIGHT));

        // Table Rows
        int srNo = 1;
        if (quotation.getItems() != null) {
            for (QuotationItemResponseDto item : quotation.getItems()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(srNo++)))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(item.getServiceDescription())));
                table.addCell(new Cell().add(new Paragraph(item.getPrice().toPlainString()))
                        .setTextAlignment(TextAlignment.RIGHT));
            }
        }

        // --- 4. PROFESSIONAL COST BREAKDOWN ---

        // Subtotal
        Cell subtotalLabel = new Cell(1, 2).add(new Paragraph("Subtotal").setBold())
                .setTextAlignment(TextAlignment.RIGHT);
        table.addCell(subtotalLabel);
        table.addCell(new Cell().add(new Paragraph(quotation.getSubtotal() != null ? quotation.getSubtotal().toPlainString() : "0.00").setBold())
                .setTextAlignment(TextAlignment.RIGHT));

        // Discount (Appends the percentage rate directly from the DTO)
        if (quotation.getDiscountAmount() != null && quotation.getDiscountAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            String discountRateStr = quotation.getDiscountPercent() != null ? quotation.getDiscountPercent().toPlainString() + "%" : "";
            Cell discountLabel = new Cell(1, 2).add(new Paragraph("Discount (" + discountRateStr + ")").setBold())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(ColorConstants.RED); // Optional: Make discounts slightly red for clarity
            table.addCell(discountLabel);

            table.addCell(new Cell().add(new Paragraph("- " + quotation.getDiscountAmount().toPlainString()).setBold())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(ColorConstants.RED));
        }

        // Tax (Appends the percentage rate directly from the DTO)
        if (quotation.getTaxAmount() != null && quotation.getTaxAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            String taxRateStr = quotation.getTaxPercent() != null ? quotation.getTaxPercent().toPlainString() + "%" : "";
            Cell taxLabel = new Cell(1, 2).add(new Paragraph("Taxes (" + taxRateStr + ")").setBold())
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(taxLabel);

            table.addCell(new Cell().add(new Paragraph("+ " + quotation.getTaxAmount().toPlainString()).setBold())
                    .setTextAlignment(TextAlignment.RIGHT));
        }

        // Total Payable Amount
        Cell totalLabel = new Cell(1, 2).add(new Paragraph("Total Payable Amount (INR)").setBold())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY); // Highlighting the final total row
        table.addCell(totalLabel);
        table.addCell(new Cell().add(new Paragraph(quotation.getTotalAmount() != null ? quotation.getTotalAmount().toPlainString() : "0.00").setBold())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY));

        document.add(table);

        // --- 5. FOOTER / TERMS ---
        document.add(new Paragraph("Terms & Conditions:")
                .setBold()
                .setMarginTop(30f)
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("1. Payment is due upon receipt of this quotation.\n2. Please make checks payable to RA & RA Counsels.")
                .setFontSize(9f)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.LEFT));

        document.close();
        return baos.toByteArray();
    }

    public byte[] generateInvoicePdf(InvoiceResponseDto invoice) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        ClassPathResource imgResource = new ClassPathResource("letterhead_bg.png");
        try (InputStream is = imgResource.getInputStream()) {
            byte[] imageBytes = is.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image bgImage = new Image(imageData);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new LetterheadBackgroundHandler(bgImage));
        }

        Document document = new Document(pdf, PageSize.A4);
        // Margins depend on your letterhead graphic (Top, Right, Bottom, Left)
        document.setMargins(240f, 72f, 120f, 72f);

        // --- 1. PROFESSIONAL HEADER ---
        document.add(new Paragraph("TAX INVOICE")
                .setBold()
                .setFontSize(18f)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15f));

        // --- 2. META DATA (Bill To, Invoice No, Date, Due Date) ---
        float[] metaWidths = {1, 1};
        Table metaTable = new Table(UnitValue.createPercentArray(metaWidths)).useAllAvailableWidth();
        metaTable.setMarginBottom(25f);

        // Left Side: Bill To
        Cell billToCell = new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
        billToCell.add(new Paragraph("Bill To:").setBold().setFontSize(10f).setFontColor(ColorConstants.GRAY));
        billToCell.add(new Paragraph(invoice.getCompanyName() != null ? invoice.getCompanyName() : "Client Name")
                .setBold().setFontSize(12f));

        // Right Side: Invoice Details
        Cell detailsCell = new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);

        String invNumber = invoice.getInvoiceNumber() != null ? invoice.getInvoiceNumber() : "TBD";
        detailsCell.add(new Paragraph("Invoice No: " + invNumber).setBold().setFontSize(10f));

        // Format the Issue Date
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy");
        if (invoice.getCreatedAt() != null) {
            String formattedDate = invoice.getCreatedAt().format(formatter);
            detailsCell.add(new Paragraph("Issue Date: " + formattedDate).setFontSize(10f));
        }

        // Format the Due Date
        if (invoice.getDueDate() != null) {
            String formattedDueDate = invoice.getDueDate().format(formatter);
            detailsCell.add(new Paragraph("Due Date: " + formattedDueDate).setFontSize(10f).setFontColor(ColorConstants.RED));
        }

        metaTable.addCell(billToCell);
        metaTable.addCell(detailsCell);
        document.add(metaTable);

        // --- 3. LINE ITEMS TABLE ---
        float[] columnWidths = {1, 7, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
        table.setMarginBottom(20f);

        // Table Headers
        table.addHeaderCell(new Cell().add(new Paragraph("Sr. No.").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Service Description").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount (INR)").setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.RIGHT));

        // Table Rows
        int srNo = 1;
        if (invoice.getItems() != null) {
            for (InvoiceItemResponseDto item : invoice.getItems()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(srNo++)))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(item.getServiceDescription())));
                table.addCell(new Cell().add(new Paragraph(item.getPrice().toPlainString()))
                        .setTextAlignment(TextAlignment.RIGHT));
            }
        }

        // --- 4. PROFESSIONAL COST BREAKDOWN ---

        // Subtotal
        Cell subtotalLabel = new Cell(1, 2).add(new Paragraph("Subtotal").setBold())
                .setTextAlignment(TextAlignment.RIGHT);
        table.addCell(subtotalLabel);
        table.addCell(new Cell().add(new Paragraph(invoice.getSubtotal() != null ? invoice.getSubtotal().toPlainString() : "0.00").setBold())
                .setTextAlignment(TextAlignment.RIGHT));

        // Discount
        if (invoice.getDiscountAmount() != null && invoice.getDiscountAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            String discountRateStr = invoice.getDiscountPercent() != null ? invoice.getDiscountPercent().toPlainString() + "%" : "";
            Cell discountLabel = new Cell(1, 2).add(new Paragraph("Discount (" + discountRateStr + ")").setBold())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(ColorConstants.RED);
            table.addCell(discountLabel);

            table.addCell(new Cell().add(new Paragraph("- " + invoice.getDiscountAmount().toPlainString()).setBold())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(ColorConstants.RED));
        }

        // Tax
        if (invoice.getTaxAmount() != null && invoice.getTaxAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            String taxRateStr = invoice.getTaxPercent() != null ? invoice.getTaxPercent().toPlainString() + "%" : "";
            Cell taxLabel = new Cell(1, 2).add(new Paragraph("Taxes (" + taxRateStr + ")").setBold())
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(taxLabel);

            table.addCell(new Cell().add(new Paragraph("+ " + invoice.getTaxAmount().toPlainString()).setBold())
                    .setTextAlignment(TextAlignment.RIGHT));
        }

        // Total Payable Amount
        Cell totalLabel = new Cell(1, 2).add(new Paragraph("Total Payable Amount (INR)").setBold())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
        table.addCell(totalLabel);
        table.addCell(new Cell().add(new Paragraph(invoice.getTotalAmount() != null ? invoice.getTotalAmount().toPlainString() : "0.00").setBold())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY));

        document.add(table);

        // --- 5. FOOTER / TERMS ---
        document.add(new Paragraph("Payment Instructions:")
                .setBold()
                .setMarginTop(30f)
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("1. Please make the payment by the due date mentioned above.\n2. Make checks payable to RA & RA Counsels or use the attached bank transfer details.")
                .setFontSize(9f)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.LEFT));

        document.close();
        return baos.toByteArray();
    }
}