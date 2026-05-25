package com.exponab.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exponab.invoice.dto.request.PurchaseOrderRequestDto;
import com.exponab.invoice.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

	@Autowired
    private PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody PurchaseOrderRequestDto request) {

        return ResponseEntity.ok(service.createPO(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(service.getAll());
    }
}
