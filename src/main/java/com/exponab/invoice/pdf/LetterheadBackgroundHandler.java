package com.exponab.invoice.pdf;

import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;

public class LetterheadBackgroundHandler implements IEventHandler {

    private final Image backgroundImage;

    public LetterheadBackgroundHandler(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public void handleEvent(com.itextpdf.kernel.events.Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Rectangle area = page.getPageSize();

        backgroundImage.scaleAbsolute(area.getWidth(), area.getHeight());
        backgroundImage.setFixedPosition(0, 0);

        try (Canvas canvas = new Canvas(pdfCanvas, area)) {
            canvas.add(backgroundImage);
        }
    }
}