package com.exponab.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exponab.invoice.dto.request.PurchaseOrderRequestDto;
import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.service.PurchaseOrderEmailService;
import com.exponab.invoice.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
@PreAuthorize("hasRole('ADMIN')")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService service;

    @Autowired
    private PurchaseOrderEmailService emailService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PurchaseOrderRequestDto request) {
        return ResponseEntity.ok(service.createPO(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/{id}/send-mail")
    public ResponseEntity<ApiResponse<String>> sendMail(@PathVariable("id") Long id) {
        emailService.sendPurchaseOrder(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Purchase Order email sent successfully", "MAIL_SENT")
        );
    }
}