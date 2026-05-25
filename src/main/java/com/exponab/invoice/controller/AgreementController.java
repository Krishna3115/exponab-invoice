package com.exponab.invoice.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exponab.invoice.dto.request.AgreementRequestDto;
import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.response.ClauseResponseDto;
import com.exponab.invoice.service.AgreementService;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/agreements")
@PreAuthorize("hasRole('ADMIN')")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @PostMapping("/draft")
    public ResponseEntity<ApiResponse<Void>> saveDraft(@RequestBody AgreementRequestDto dto) {
        agreementService.saveAgreementDraft(dto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Draft saved successfully", null));
    }

    @PostMapping("/finalize/{companyId}")
    public ResponseEntity<ApiResponse<Void>> finalizeAgreement(
            @PathVariable Long companyId,
            @RequestBody DocumentGenerationRequest request) {
        agreementService.finalizeAndSend(companyId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Agreement finalized and emailed successfully", null));
    }

    @GetMapping("/clauses/{companyId}")
    public ResponseEntity<ApiResponse<List<ClauseResponseDto>>> getClauses(@PathVariable Long companyId) {
        List<ClauseResponseDto> clauses = agreementService.getClausesByCompany(companyId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Clauses fetched successfully", clauses));
    }

    @PutMapping("/clauses/{clauseId}")
    public ResponseEntity<ApiResponse<ClauseResponseDto>> updateClause(
            @PathVariable Long clauseId,
            @RequestBody Map<String, String> body) {

        String newContent = body.get("clauseContent");
        ClauseResponseDto updatedClause = agreementService.updateClause(clauseId, newContent);
        
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Clause updated successfully", updatedClause));
    }

    @DeleteMapping("/clauses/{clauseId}")
    public ResponseEntity<ApiResponse<Void>> deleteClause(@PathVariable Long clauseId) {
        agreementService.deleteClause(clauseId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Clause deleted successfully", null));
    }
}