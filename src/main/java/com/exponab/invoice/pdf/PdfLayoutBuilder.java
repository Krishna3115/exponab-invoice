package com.exponab.invoice.pdf;

import com.exponab.invoice.entity.PurchaseOrder;
import com.exponab.invoice.entity.PurchaseOrderItem;
import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.entity.SalesReportExpense;
import com.exponab.invoice.entity.SalesReportItem;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PdfLayoutBuilder {

    // Default currency when none set on the report
    private static final String DEFAULT_CURRENCY = "AED";

    // Your company info (top-right of page 1)
    private static final String COMPANY_NAME    = "EXPONAB GENERAL TRADING LLC";
    private static final String COMPANY_ADDR_1  = "AL AWEER CENTRAL FRUITS";
    private static final String COMPANY_ADDR_2  = "AND VEGETABLES MARKET DUBAI UAE";
    private static final String COMPANY_TRN     = "TRN: 105282652400003";
    private static final String COMPANY_PHONE   = "+971 547410141";
    private static final String COMPANY_EMAIL   = "BUSINESS@EXPONAB.COM";

    public static final List<String> STANDARD_EXPENSES = Arrays.asList(
            "DO CHARGES",
            "BILL OF ENTRY",
            "DPW CHARGES",
            "TRANSPORTATION",
            "TOKEN CHARGES",
            "DOCUMENT CLEARING CHARGES",
            "ZAJEL COURIER CHARGES",
            "MOFA INVOICE ATTESTATION",
            "INSPECTION",
            "DECLARATION AMENDMENT CHARGES",
            "VAT",
            "TASHRIYA PARKING & PLUG IN",
            "UNLOADING / HAMALI",
            "COMMISSION",
            "LOCAL TRANSPORT"
    );

    // =========================================================
    // SALES REPORT (3 pages)
    // =========================================================
    public static byte[] buildSalesReport(SalesReport report) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(170, 40, 120, 40);

        try {
            Image bg = getHeaderImageSafely();
            if (bg != null) {
                pdf.addEventHandler(
                        PdfDocumentEvent.START_PAGE,
                        new LetterheadBackgroundHandler(bg)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addSalesReportPage(document, report);
        document.add(new AreaBreak());

        addExpensePage(document, report);
        document.add(new AreaBreak());

        addFinalSummary(document, report);

        document.close();
        return out.toByteArray();
    }

    // =========================================================
    // PURCHASE ORDER
    // =========================================================
 // =========================================================
 // PURCHASE ORDER (4-page document matching reference layout)
 // =========================================================
 public static byte[] buildPurchaseOrder(PurchaseOrder po) {

     ByteArrayOutputStream out = new ByteArrayOutputStream();
     PdfWriter writer = new PdfWriter(out);
     PdfDocument pdf = new PdfDocument(writer);
     Document document = new Document(pdf);

     document.setMargins(170, 40, 120, 40);

     // Letterhead background on every page
     try {
         Image bg = getHeaderImageSafely();
         if (bg != null) {
             pdf.addEventHandler(
                     PdfDocumentEvent.START_PAGE,
                     new LetterheadBackgroundHandler(bg)
             );
         }
     } catch (Exception e) {
         e.printStackTrace();
     }

     String currency = (po.getCurrency() == null || po.getCurrency().isBlank())
             ? "USD" : po.getCurrency();

     // ═══════════════════════════════════════════════════════════
     // PAGE 1 — Header, Importer, Exporter, Shipment
     // ═══════════════════════════════════════════════════════════

     document.add(new Paragraph("DATE: " + po.getPoDate())
             .setBold().setFontSize(11)
             .setTextAlignment(TextAlignment.RIGHT));

     document.add(new Paragraph("\n"));

     document.add(new Paragraph("PURCHASE ORDER (PO)")
             .setBold().setFontSize(14));

     document.add(new Paragraph("PO Number: " + safeStr(po.getPoNumber()))
             .setBold().setFontSize(11));
     document.add(new Paragraph("PO Date: " + po.getPoDate())
             .setBold().setFontSize(11));

     document.add(new Paragraph("\n"));

     // IMPORTER (Exponab — hardcoded)
     document.add(new Paragraph("IMPORTER").setBold().setFontSize(12));
     document.add(new Paragraph("Company Name: EXPONAB GENERAL TRADING LLC").setBold());
     document.add(new Paragraph("Address: Al Aweer Central Fruits & Vegetables Market, Dubai, United Arab Emirates").setBold());
     document.add(new Paragraph("Email: business@exponab.com").setBold());

     document.add(new Paragraph("\n"));

     // EXPORTER (from selected supplier)
     document.add(new Paragraph("EXPORTER").setBold().setFontSize(12));
     document.add(new Paragraph("Company Name: "
             + safeStr(po.getExporter().getCompanyName())).setBold());
     document.add(new Paragraph("Address: "
             + safeStr(po.getExporter().getAddress())).setBold());

     if (po.getExporter().getEmail() != null && !po.getExporter().getEmail().isBlank()) {
         document.add(new Paragraph("Email: " + po.getExporter().getEmail()).setBold());
     }

     document.add(new Paragraph("\n"));

     // SHIPMENT DETAILS
     document.add(new Paragraph("SHIPMENT DETAILS").setBold().setFontSize(12));

     com.itextpdf.layout.element.List shipList =
             new com.itextpdf.layout.element.List()
                     .setListSymbol("\u2022  ")
                     .setMarginLeft(15);
     shipList.add(new ListItem("Country of Origin: " + safeStr(po.getCountryOfOrigin())));
     shipList.add(new ListItem("Destination Port: " + safeStr(po.getDestinationPort())));
     shipList.add(new ListItem("Incoterms: " + safeStr(po.getIncoterms())));
     shipList.add(new ListItem("Mode of Transport: " + safeStr(po.getTransportMode())));
     document.add(shipList);

     document.add(new AreaBreak());

     // ═══════════════════════════════════════════════════════════
     // PAGE 2 — Product, Pricing, Quality, Payment intro
     // ═══════════════════════════════════════════════════════════

     document.add(new Paragraph("PRODUCT DETAILS").setBold().setFontSize(13));
     document.add(new Paragraph("Commodity: " + safeStr(po.getCommodity())).setBold());
     document.add(new Paragraph("Quality: " + safeStr(po.getQuality())).setBold());
     document.add(new Paragraph("Packaging: " + safeStr(po.getPackaging())).setBold());
     document.add(new Paragraph("Quantity: " + safeStr(po.getTotalQuantity())).setBold());
     document.add(new Paragraph("Price: " + safeStr(po.getPriceNote())).setBold());

     document.add(new Paragraph("\n"));

     document.add(new Paragraph("PRICING").setBold().setFontSize(13));

     if (po.getExchangeRate() != null && "USD".equalsIgnoreCase(currency)) {
         document.add(new Paragraph("1 USD = " + po.getExchangeRate() + " AED")
                 .setBold().setTextAlignment(TextAlignment.CENTER));
     }

     document.add(new Paragraph("\n"));

     Table table = new Table(
             UnitValue.createPercentArray(new float[]{1, 4, 3, 2, 3}))
             .setWidth(UnitValue.createPercentValue(100));

     addHeader(table, "SR.NO.");
     addHeader(table, "DESCRIPTION");
     addHeader(table, "QUANTITY");
     addHeader(table, "PRICE/BAG");
     addHeader(table, "TOTAL");

     int sr = 1;
     for (PurchaseOrderItem item : po.getItems()) {
         table.addCell(centerCell(String.valueOf(sr++)));
         table.addCell(centerCell(safeStr(item.getCommodity())).setBold());
         table.addCell(centerCell(item.getQuantity() + " " + safeStr(item.getUnit())));
         table.addCell(centerCell(item.getUnitPrice() + " " + currency));
         table.addCell(centerCell(item.getTotalPrice() + " " + currency));
     }

     document.add(table);

     document.add(new Paragraph("\n"));

     // QUALITY & STANDARDS (hardcoded)
     document.add(new Paragraph("QUALITY & STANDARDS").setBold().setFontSize(13));

     com.itextpdf.layout.element.List qualityList =
             new com.itextpdf.layout.element.List()
                     .setListSymbol("\u2022  ")
                     .setMarginLeft(15);
     qualityList.add(new ListItem(
             "Goods must be fresh, mature, clean, and free from pests, decay, or damage."));
     qualityList.add(new ListItem(
             "Produce must comply with UAE food safety regulations and international export standards."));
     document.add(qualityList);

     document.add(new Paragraph("\n"));

     document.add(new Paragraph("PAYMENT TERMS").setBold().setFontSize(13));
     document.add(new Paragraph("Payment Method: " + safeStr(po.getPaymentTerms())).setBold());

     document.add(new AreaBreak());

     // ═══════════════════════════════════════════════════════════
     // PAGE 3 — Payment continuation + Commission (hardcoded)
     // ═══════════════════════════════════════════════════════════

     com.itextpdf.layout.element.List payList =
             new com.itextpdf.layout.element.List()
                     .setListSymbol("\u2022  ")
                     .setMarginLeft(15);

     payList.add(new ListItem(
             "Payment Terms: Buyer agrees to make 100% payment within 15\u201320 days "
           + "from the date the containers arrive at Jebel Ali Port and are withdrawn by the Buyer."));
     payList.add(new ListItem(
             "Payment shall be made based on the actual sales realization of the consignment, "
           + "after deduction of a 5% commission on the total realized sales value."));

     document.add(payList);
     document.add(new Paragraph("\n"));

     document.add(new Paragraph("COMMISSION TERMS").setBold().setFontSize(13));

     com.itextpdf.layout.element.List commList =
             new com.itextpdf.layout.element.List()
                     .setListSymbol("\u2022  ")
                     .setMarginLeft(15);
     commList.add(new ListItem(
             "A commission of 5% shall be applicable on the actual sales value "
           + "realized from the sale of the goods in the UAE market."));
     commList.add(new ListItem(
             "The commission shall be calculated based on the net realized "
           + "selling price of the consignment."));
     commList.add(new ListItem(
             "Payment of commission to the Seller shall be made along with the "
           + "settlement of the consignment as per agreed payment terms."));
     commList.add(new ListItem(
             "Both parties agree that commission is directly linked to market "
           + "sales performance and final realized value."));
     document.add(commList);

     document.add(new AreaBreak());

     // ═══════════════════════════════════════════════════════════
     // PAGE 4 — Delivery (hardcoded) + Stamp + Footer
     // ═══════════════════════════════════════════════════════════

     document.add(new Paragraph("DELIVERY TERMS").setBold().setFontSize(13));

     com.itextpdf.layout.element.List delList =
             new com.itextpdf.layout.element.List()
                     .setListSymbol("\u2022  ")
                     .setMarginLeft(15);
     delList.add(new ListItem(
             "Delivery shall be executed as per agreed Incoterms\u00ae ("
           + safeStr(po.getIncoterms()) + ")."));
     delList.add(new ListItem(
             "Seller is responsible for proper export packaging, container stuffing, "
           + "and securing cargo to prevent transit damage."));
     document.add(delList);

     document.add(new Paragraph("\n\n"));

     Image stamp = getStampImageSafely();
     if (stamp != null) {
         stamp.setWidth(140);
         stamp.setHorizontalAlignment(HorizontalAlignment.LEFT);
         document.add(stamp);
     }

     document.add(new Paragraph("EXPONAB GENERAL TRADING LLC")
             .setBold().setFontSize(12));

     document.close();
     return out.toByteArray();
 }
    // =========================================================
    // PAGE 1 — SALES REPORT
    // Top-right: Company info block (Exponab address, TRN, contact)
    // Below that: BILL TO (left) + Report meta (right)
    // =========================================================
    private static void addSalesReportPage(Document document, SalesReport report) {

        // Title
        document.add(new Paragraph("SALES REPORT")
                .setBold().setFontSize(20)
                .setTextAlignment(TextAlignment.RIGHT));

        // OUR COMPANY INFO — right-aligned, just under the title
        document.add(new Paragraph(COMPANY_NAME).setBold()
                .setFontSize(10).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(COMPANY_ADDR_1)
                .setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(COMPANY_ADDR_2)
                .setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(COMPANY_TRN)
                .setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(COMPANY_PHONE)
                .setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(COMPANY_EMAIL)
                .setFontSize(9).setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("\n"));

        // BILL TO block — left
        document.add(new Paragraph("BILL TO").setBold().setFontSize(11));
        document.add(new Paragraph(safeStr(report.getCustomer().getCompanyName()))
                .setBold().setFontSize(10));
        document.add(new Paragraph(safeStr(report.getCustomer().getAddress()))
                .setFontSize(9));

        if (report.getCustomer().getPhone() != null
                && !report.getCustomer().getPhone().isBlank()) {
            document.add(new Paragraph(report.getCustomer().getPhone())
                    .setFontSize(9));
        }

        // Report meta strip — small table aligned to the right
        document.add(new Paragraph("\n"));

        Table meta = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .setWidth(UnitValue.createPercentValue(100));

        meta.addCell(getBorderlessCell(""));
        meta.addCell(getBorderlessCell(
                "Report No: " + safeStr(report.getReportNumber())
                + "\nDate: " + report.getReportDate()
                + "\nContainer No: " + safeStr(report.getContainerNumber()))
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(meta);
        document.add(new Paragraph("\n"));

        // Items table
        String currency = currencyOf(report);

        float[] cols = {1, 5, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(cols))
                .setWidth(UnitValue.createPercentValue(100));

        addHeader(table, "Sr No");
        addHeader(table, "Description of Goods");
        addHeader(table, "Quantity");
        addHeader(table, "Rate");
        addHeader(table, "Amount");

        int sr = 1;
        BigDecimal totalQty = BigDecimal.ZERO;

        for (SalesReportItem item : report.getItems()) {
            table.addCell(centerCell(String.valueOf(sr++)));
            table.addCell(bulletCell(item.getDescription()));
            table.addCell(centerCell(item.getQuantity().toString()));
            table.addCell(centerCell(currency + " " + item.getUnitPrice()));
            table.addCell(centerCell(currency + " " + item.getTotalPrice()));

            totalQty = totalQty.add(item.getQuantity());
        }

        table.addCell(new Cell(1, 2)
                .add(new Paragraph("Grand Total").setBold())
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(centerCell(totalQty.toString()));
        table.addCell(new Cell());
        table.addCell(centerCell(currency + " " + report.getGrandTotal()).setBold());

        document.add(table);

        addStampAndSignature(document);
    }

    // =========================================================
    // PAGE 2 — EXPENSE SHEET
    // =========================================================
    private static void addExpensePage(Document document, SalesReport report) {

        document.add(new Paragraph("EXPENSE SHEET")
                .setBold().setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        Table headerStrip = new Table(
                UnitValue.createPercentArray(new float[]{4, 3, 3}))
                .setWidth(UnitValue.createPercentValue(100));

        headerStrip.addCell(new Cell()
                .add(new Paragraph("TO: "
                        + safeStr(report.getCustomer().getCompanyName())).setBold()));
        headerStrip.addCell(new Cell()
                .add(new Paragraph("CONTAINER\n"
                        + safeStr(report.getContainerNumber())).setBold())
                .setTextAlignment(TextAlignment.CENTER));
        headerStrip.addCell(new Cell()
                .add(new Paragraph("DATE: " + report.getReportDate()).setBold())
                .setTextAlignment(TextAlignment.CENTER));

        document.add(headerStrip);
        document.add(new Paragraph("\n"));

        String currency = currencyOf(report);

        float[] cols = {1, 5, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(cols))
                .setWidth(UnitValue.createPercentValue(100));

        addHeader(table, "Sr No");
        addHeader(table, "Description");
        addHeader(table, "Quantity");
        addHeader(table, "Unit Rate");
        addHeader(table, "Amount");

        int sr = 1;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (SalesReportExpense exp : report.getExpenses()) {
            table.addCell(centerCell(String.valueOf(sr++)));
            table.addCell(leftCell(safeStr(exp.getDescription())));
            table.addCell(centerCell(exp.getQuantity().toString()));
            table.addCell(centerCell(currency + " " + exp.getUnitRate()));
            table.addCell(centerCell(currency + " " + exp.getAmount()));
            totalExpense = totalExpense.add(exp.getAmount());
        }

        // TOTAL row
        table.addCell(new Cell(1, 4)
                .add(new Paragraph("TOTAL").setBold())
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(centerCell(currency + " " + totalExpense).setBold());

        // DISCOUNT row (placeholder — matches reference layout)
        table.addCell(new Cell(1, 4)
                .add(new Paragraph("DISCOUNT").setBold())
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(centerCell(""));

        // FINAL TOTAL row
        table.addCell(new Cell(1, 4)
                .add(new Paragraph("TOTAL").setBold())
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(centerCell(currency + " " + totalExpense).setBold());

        document.add(table);

        addStampAndSignature(document);
    }

    // =========================================================
    // PAGE 3 — INVOICE
    // =========================================================
    private static void addFinalSummary(Document document, SalesReport report) {

        BigDecimal expenses = report.getExpenses().stream()
                .map(SalesReportExpense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal payable = report.getGrandTotal().subtract(expenses);
        String currency = currencyOf(report);

        document.add(new Paragraph("INVOICE")
                .setBold().setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        Table headerStrip = new Table(
                UnitValue.createPercentArray(new float[]{4, 3, 3}))
                .setWidth(UnitValue.createPercentValue(100));

        headerStrip.addCell(new Cell()
                .add(new Paragraph("TO: "
                        + safeStr(report.getCustomer().getCompanyName())).setBold()));
        headerStrip.addCell(new Cell()
                .add(new Paragraph("INVOICE NO: "
                        + safeStr(report.getReportNumber())).setBold())
                .setTextAlignment(TextAlignment.CENTER));
        headerStrip.addCell(new Cell()
                .add(new Paragraph("DATE: " + report.getReportDate()).setBold())
                .setTextAlignment(TextAlignment.CENTER));

        document.add(headerStrip);
        document.add(new Paragraph("\n"));

        Table table = new Table(
                UnitValue.createPercentArray(new float[]{1, 6, 3}))
                .setWidth(UnitValue.createPercentValue(100));

        addHeader(table, "Sr No");
        addHeader(table, "Description");
        addHeader(table, "Amount");

        table.addCell(centerCell("1"));
        table.addCell(leftCell("CONTAINER NO: " + safeStr(report.getContainerNumber())
                + " SALES AMOUNT").setBold());
        table.addCell(centerCell(currency + " " + report.getGrandTotal()).setBold());

        table.addCell(centerCell("2"));
        table.addCell(leftCell("TOTAL EXPENSES OF CONTAINER NO: "
                + safeStr(report.getContainerNumber())).setBold());
        table.addCell(centerCell(currency + " " + expenses).setBold());

        // Blank rows to match the reference invoice layout
        for (int i = 0; i < 5; i++) {
            table.addCell(new Cell());
            table.addCell(new Cell());
            table.addCell(new Cell());
        }

        table.addCell(new Cell(1, 2)
                .add(new Paragraph("Total Payable").setBold())
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(centerCell(currency + " " + payable).setBold());

        document.add(table);

        addStampAndSignature(document);
    }

    // =========================================================
    // STAMP + SIGNATURE (used on all pages)
    // =========================================================
    private static void addStampAndSignature(Document document) {

        document.add(new Paragraph("\n"));

        Image stamp = getStampImageSafely();
        if (stamp != null) {
            stamp.setWidth(140);   // bigger; was 110
            stamp.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(stamp);
        }

        document.add(new Paragraph("For, Exponab General Trading LLC")
                .setBold().setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Authorized Signatory")
                .setTextAlignment(TextAlignment.LEFT));
    }

    // =========================================================
    // HELPERS
    // =========================================================
    private static void addHeader(Table table, String title) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(title).setBold())
                .setTextAlignment(TextAlignment.CENTER));
    }

    private static Cell centerCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell leftCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.LEFT);
    }

    private static Cell getBorderlessCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setBorder(Border.NO_BORDER);
    }

    private static Cell bulletCell(String text) {

        Cell cell = new Cell().setTextAlignment(TextAlignment.LEFT);

        if (text == null || text.isBlank()) {
            cell.add(new Paragraph(""));
            return cell;
        }

        String[] lines = text.split("\\r?\\n");

        if (lines.length == 1) {
            cell.add(new Paragraph(lines[0]));
            return cell;
        }

        com.itextpdf.layout.element.List bulletList =
                new com.itextpdf.layout.element.List()
                        .setListSymbol("\u2022  ")
                        .setMarginLeft(8);

        for (String line : lines) {
            if (!line.isBlank()) {
                bulletList.add(new ListItem(line.trim()));
            }
        }

        cell.add(bulletList);
        return cell;
    }

    /** For text fields — blank stays blank, doesn't show "-". */
    private static String safeStr(String s) {
        return (s == null) ? "" : s;
    }

    /** Returns the report's currency symbol, or "AED" if missing. */
    private static String currencyOf(SalesReport report) {
        String c = report.getCurrencySymbol();
        if (c == null || c.isBlank()) {
            return DEFAULT_CURRENCY;
        }
        return c;
    }

    // =========================================================
    // LETTERHEAD IMAGE
    // =========================================================
    private static Image getHeaderImageSafely() {
        try {
            InputStream stream = PdfLayoutBuilder.class.getClassLoader()
                    .getResourceAsStream("static/letterheadexponab.jpeg");
            if (stream == null) return null;

            byte[] bytes = stream.readAllBytes();
            ImageData imageData = ImageDataFactory.create(bytes);
            return new Image(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =========================================================
    // STAMP IMAGE
    // PNG with transparent background required for best result.
    // =========================================================
    private static Image getStampImageSafely() {
        try {
            InputStream stream = PdfLayoutBuilder.class.getClassLoader()
                    .getResourceAsStream("static/exponab_stamp.png");
            if (stream == null) {
                stream = PdfLayoutBuilder.class.getClassLoader()
                        .getResourceAsStream("static/exponab_stamp.jpeg");
                if (stream == null) return null;
            }
            byte[] bytes = stream.readAllBytes();
            ImageData imageData = ImageDataFactory.create(bytes);
            return new Image(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}