package com.exponab.invoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exponab.invoice.dto.request.ExpenseMasterRequestDto;
import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.response.ExpenseMasterResponseDto;
import com.exponab.invoice.service.ExpenseMasterService;

@RestController
@RequestMapping("/api/expense-master")
@PreAuthorize("hasRole('ADMIN')")
public class ExpenseMasterController {

	private final ExpenseMasterService service;

    public ExpenseMasterController(
            ExpenseMasterService service) {

        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>>
    create(
            @RequestBody ExpenseMasterRequestDto request) {

        ExpenseMasterResponseDto response =
                service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Expense created successfully",
                                response
                        )
                );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseMasterResponseDto>>>
    getAll() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expenses fetched successfully",
                        service.getAll()
                )
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>>
    getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expense fetched successfully",
                        service.getById(id)
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>>
    update(
            @PathVariable Long id,
            @RequestBody ExpenseMasterRequestDto request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expense updated successfully",
                        service.update(id, request)
                )
        );
    }

    // DEACTIVATE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deactivate(@PathVariable Long id) {

        service.deactivate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expense deactivated successfully",
                        null
                )
        );
    }
}
