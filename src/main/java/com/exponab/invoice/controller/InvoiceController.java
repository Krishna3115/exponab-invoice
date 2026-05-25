package com.exponab.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.request.InvoiceRequestDto;
import com.exponab.invoice.dto.request.InvoiceSendRequestDto;
import com.exponab.invoice.dto.response.InvoiceResponseDto;
import com.exponab.invoice.service.InvoiceService;

import jakarta.validation.Valid;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/invoices")
@PreAuthorize("hasRole('ADMIN')")
public class InvoiceController {

    @Autowired 
    private InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> generate(@Valid @RequestBody InvoiceRequestDto dto) {
        InvoiceResponseDto responseDto = invoiceService.createInvoiceFromQuotation(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Invoice generated successfully", responseDto));
    }

    @DeleteMapping("/{invoiceId}/items/{itemId}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> removeItem(@PathVariable Long invoiceId, @PathVariable Long itemId) {
        // Notice we return the UPDATED invoice back to the user
        InvoiceResponseDto updatedInvoice = invoiceService.removeItemFromInvoice(invoiceId, itemId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Item removed and totals updated successfully", updatedInvoice));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponseDto>>> getAllInvoices() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Invoices fetched successfully", invoiceService.getAllInvoices()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Invoice fetched successfully", invoiceService.getInvoiceById(id)));
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<ApiResponse<Void>> sendInvoiceEmail(
            @PathVariable Long id, 
            @Valid @RequestBody InvoiceSendRequestDto request) {
        
        invoiceService.generateAndSendInvoice(id, request);
        
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Tax Invoice PDF generated and emailed successfully", null));
    }
}