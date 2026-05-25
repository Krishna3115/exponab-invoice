package com.exponab.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.request.QuotationRequestDto;
import com.exponab.invoice.dto.request.QuotationSendRequestDto;
import com.exponab.invoice.dto.response.QuotationResponseDto;
import com.exponab.invoice.service.QuotationService;

import jakarta.validation.Valid;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quotations")
@PreAuthorize("hasRole('ADMIN')")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuotationResponseDto>> createQuotation(@Valid @RequestBody QuotationRequestDto dto) {
        QuotationResponseDto responseDto = quotationService.createQuotation(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Quotation created successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuotationResponseDto>>> getAllQuotations() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Quotations fetched successfully", quotationService.getAllQuotations()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuotationResponseDto>> getQuotationById(@PathVariable String id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Quotation fetched successfully", quotationService.getQuotationById(id)));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<QuotationResponseDto>>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Company quotations fetched successfully", quotationService.getQuotationsByCompany(companyId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuotation(@PathVariable Long id) {
        quotationService.deleteQuotation(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Quotation deleted successfully", null));
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<ApiResponse<Void>> sendQuotationEmail(
            @PathVariable Long id, 
            @Valid @RequestBody QuotationSendRequestDto request) {
        quotationService.generateAndSendQuotation(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Quotation PDF generated and emailed successfully", null));
    }
}