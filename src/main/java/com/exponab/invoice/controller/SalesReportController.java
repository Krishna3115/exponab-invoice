package com.exponab.invoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exponab.invoice.dto.request.SalesReportRequestDto;
import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.response.SalesReportDetailsResponseDto;
import com.exponab.invoice.dto.response.SalesReportResponseDto;
import com.exponab.invoice.service.SalesReportEmailService;
import com.exponab.invoice.service.SalesReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales-reports")
@PreAuthorize("hasRole('ADMIN')")
public class SalesReportController {

	private final SalesReportService service;

	private final SalesReportEmailService emailService;
	
    public SalesReportController(
            SalesReportService service,
            SalesReportEmailService emailService) {

        this.service = service;
        this.emailService =emailService;
    }

    // CREATE SALES REPORT
    @PostMapping
    public ResponseEntity<ApiResponse<SalesReportResponseDto>> createReport(
            @Valid @RequestBody SalesReportRequestDto request) {

        SalesReportResponseDto response =
                service.createReport(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Sales Report created successfully",
                                response
                        )
                );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesReportResponseDto>>
    getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Sales Report fetched successfully",
                        service.getById(id)
                )
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<SalesReportResponseDto>>>
    getAll() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Sales Reports fetched successfully",
                        service.getAll()
                )
        );
    }
    
    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<SalesReportDetailsResponseDto>> getDetails(@PathVariable("id") Long id) {

        SalesReportDetailsResponseDto response = service.getReportDetails(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Sales Report details fetched successfully",
                        response
                )
        );
    }
    
    @PostMapping("/{id}/send-mail")
    public ResponseEntity<ApiResponse<String>>
    sendMail(@PathVariable("id") Long id,
    		@RequestParam("email") String email) {
   

        emailService.sendSalesReport(id);

        return ResponseEntity.ok(

                new ApiResponse<>(

                        true,

                        "Sales Report email sent successfully",

                        "MAIL_SENT"
                )
        );
    }
	
}
