package com.exponab.invoice.pdf;

import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.entity.SalesReportExpense;
import com.exponab.invoice.entity.SalesReportItem;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class PdfLayoutBuilder {

    public static byte[] buildSalesReport(SalesReport report) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        // SAFE PRINT AREA
        document.setMargins(170, 40, 120, 40);

        // LETTERHEAD
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

        // PAGE 1
        addSalesReportPage(document, report);

        document.add(new AreaBreak());

        // PAGE 2
        addExpensePage(document, report);

        document.add(new AreaBreak());

        // PAGE 3
        addFinalSummary(document, report);

        document.close();

        return out.toByteArray();
    }

    // =========================================================
    // SALES REPORT PAGE
    // =========================================================

    private static void addSalesReportPage(Document document,
                                           SalesReport report) {

        Paragraph title =
                new Paragraph("SALES REPORT")
                        .setBold()
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER);

        document.add(title);

        document.add(new Paragraph("\n"));

        // CUSTOMER DETAILS

        Table customerTable =
                new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                        .setWidth(UnitValue.createPercentValue(100));

        customerTable.addCell(getBorderlessCell(
                "BILL TO\n" +
                        report.getCustomer().getCompanyName() +
                        "\n" +
                        report.getCustomer().getAddress()
        ));

        customerTable.addCell(getBorderlessCell(
                "Report No: " + report.getReportNumber() +
                        "\nDate: " + report.getReportDate()
        ).setTextAlignment(TextAlignment.RIGHT));

        document.add(customerTable);

        document.add(new Paragraph("\n"));

        // PRODUCT TABLE

        float[] cols = {1, 5, 2, 2, 2};

        Table table =
                new Table(UnitValue.createPercentArray(cols))
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

            table.addCell(leftCell(item.getDescription()));

            table.addCell(centerCell(item.getQuantity().toString()));

            table.addCell(centerCell(
                    report.getCurrencySymbol() + " " +
                            item.getUnitPrice()
            ));

            table.addCell(centerCell(
                    report.getCurrencySymbol() + " " +
                            item.getTotalPrice()
            ));

            totalQty =
                    totalQty.add(item.getQuantity());
        }

        // FINAL ROW

        table.addCell(new Cell(1, 2)
                .add(new Paragraph("Grand Total").setBold())
                .setTextAlignment(TextAlignment.RIGHT));

        table.addCell(centerCell(totalQty.toString()));

        table.addCell(new Cell());

        table.addCell(centerCell(
                report.getCurrencySymbol()
                        + " "
                        + report.getGrandTotal()
        ).setBold());

        document.add(table);

        document.add(new Paragraph("\n\n"));

        document.add(
                new Paragraph("For, Exponab General Trading LLC")
                        .setBold()
                        .setTextAlignment(TextAlignment.RIGHT)
        );

        document.add(
                new Paragraph("Authorized Signatory")
                        .setTextAlignment(TextAlignment.RIGHT)
        );
    }

    // =========================================================
    // EXPENSE PAGE
    // =========================================================

    private static void addExpensePage(Document document,
                                       SalesReport report) {

        document.add(
                new Paragraph("EXPENSE SHEET")
                        .setBold()
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER)
        );

        document.add(new Paragraph("\n"));

        float[] cols = {1, 5, 2, 2, 2};

        Table table =
                new Table(UnitValue.createPercentArray(cols))
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

            table.addCell(leftCell(exp.getDescription()));

            table.addCell(centerCell(exp.getQuantity().toString()));

            table.addCell(centerCell(
                    report.getCurrencySymbol()
                            + " "
                            + exp.getUnitRate()
            ));

            table.addCell(centerCell(
                    report.getCurrencySymbol()
                            + " "
                            + exp.getAmount()
            ));

            totalExpense =
                    totalExpense.add(exp.getAmount());
        }

        table.addCell(new Cell(1, 4)
                .add(new Paragraph("TOTAL").setBold())
                .setTextAlignment(TextAlignment.RIGHT));

        table.addCell(centerCell(
                report.getCurrencySymbol()
                        + " "
                        + totalExpense
        ).setBold());

        document.add(table);
    }

    // =========================================================
    // FINAL SUMMARY
    // =========================================================

    private static void addFinalSummary(Document document,
                                        SalesReport report) {

        BigDecimal expenses =
                report.getExpenses()
                        .stream()
                        .map(SalesReportExpense::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal payable =
                report.getGrandTotal()
                        .subtract(expenses);

        document.add(
                new Paragraph("INVOICE")
                        .setBold()
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER)
        );

        document.add(new Paragraph("\n"));

        Table table =
                new Table(UnitValue.createPercentArray(new float[]{6, 3}))
                        .setWidth(UnitValue.createPercentValue(100));

        table.addCell(leftCell(
                "Sales Amount"
        ));

        table.addCell(centerCell(
                report.getCurrencySymbol()
                        + " "
                        + report.getGrandTotal()
        ));

        table.addCell(leftCell(
                "Total Expenses"
        ));

        table.addCell(centerCell(
                report.getCurrencySymbol()
                        + " "
                        + expenses
        ));

        table.addCell(leftCell(
                "Final Payable"
        ).setBold());

        table.addCell(centerCell(
                report.getCurrencySymbol()
                        + " "
                        + payable
        ).setBold());

        document.add(table);
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private static void addHeader(Table table, String title) {

        table.addHeaderCell(
                new Cell()
                        .add(new Paragraph(title).setBold())
                        .setTextAlignment(TextAlignment.CENTER)
        );
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

    // =========================================================
    // LETTERHEAD IMAGE
    // =========================================================

    private static Image getHeaderImageSafely() {

        try {

            InputStream stream =
                    PdfLayoutBuilder.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "static/letterheadexponab.jpeg"
                            );

            if (stream == null) {
                return null;
            }

            byte[] bytes = stream.readAllBytes();

            ImageData imageData =
                    ImageDataFactory.create(bytes);

            return new Image(imageData);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}