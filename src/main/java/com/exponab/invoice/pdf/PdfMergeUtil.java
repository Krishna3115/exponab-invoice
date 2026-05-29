package com.exponab.invoice.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfMergeUtil {

    /**
     * Merges the main PDF with any number of additional PDF byte arrays.
     * Order is preserved: main first, then each attachment in the list.
     * Uses iText 7 — no PDFBox dependency.
     */
    public static byte[] merge(byte[] mainPdf, List<byte[]> attachments) throws Exception {

        if (mainPdf == null || mainPdf.length == 0) {
            throw new IllegalArgumentException("Main PDF is empty");
        }

        if (attachments == null || attachments.isEmpty()) {
            return mainPdf;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(out);
             PdfDocument destDoc = new PdfDocument(writer)) {

            PdfMerger merger = new PdfMerger(destDoc);

            // Add the main generated PDF first
            try (PdfReader reader = new PdfReader(new ByteArrayInputStream(mainPdf));
                 PdfDocument srcDoc = new PdfDocument(reader)) {

                merger.merge(srcDoc, 1, srcDoc.getNumberOfPages());
            }

            // Append each attachment
            for (byte[] attachment : attachments) {

                if (attachment == null || attachment.length == 0) {
                    continue;
                }

                try (PdfReader reader = new PdfReader(new ByteArrayInputStream(attachment));
                     PdfDocument srcDoc = new PdfDocument(reader)) {

                    merger.merge(srcDoc, 1, srcDoc.getNumberOfPages());
                }
            }
        }

        return out.toByteArray();
    }
}