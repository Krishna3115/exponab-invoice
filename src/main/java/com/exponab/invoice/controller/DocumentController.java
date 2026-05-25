package com.exponab.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.service.DocumentGenerationService;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentGenerationService documentService;

    @GetMapping(value = "/generate/{companyId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateDoc(@PathVariable Long companyId) throws Exception {
        byte[] pdfBytes = documentService.generateCompanyLetterheadPdf(companyId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"company_" + companyId + "_document.pdf\"")
                .body(pdfBytes);
    }
    @PostMapping(value = "/generate-custom", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateCustomDoc(@RequestBody DocumentGenerationRequest request) throws Exception {
        
        byte[] pdfBytes = documentService.generateCustomLetterheadPdf(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"custom_document.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
