package com.exponab.invoice.pdf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

	private final PdfService pdfService;
	
	 public PdfController(PdfService pdfService) {
	        this.pdfService = pdfService;
	    }


    @GetMapping("/sales-report/{id}")
    public ResponseEntity<byte[]> downloadSalesReport(@PathVariable("id") Long id) {

        byte[] pdf = pdfService.generateSalesReportPdf(id);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=sales-report.pdf")
                .body(pdf);
    }
}
