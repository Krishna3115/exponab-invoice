package com.exponab.invoice.controller;

import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.dto.request.ProposalRequestDto;
import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.dto.response.ProposalClauseResponseDto;
import com.exponab.invoice.service.ProposalService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/proposals")
@PreAuthorize("hasRole('ADMIN')")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping("/draft")
    public ResponseEntity<ApiResponse<Void>> saveDraft(@RequestBody ProposalRequestDto dto) {
        proposalService.saveProposalDraft(dto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal draft saved successfully", null));
    }

    @PostMapping("/finalize/{companyId}")
    public ResponseEntity<ApiResponse<Void>> finalizeProposal(
            @PathVariable Long companyId,
            @RequestBody DocumentGenerationRequest request) {
        proposalService.finalizeAndSend(companyId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal finalized and emailed successfully", null));
    }

    @GetMapping("/clauses/{companyId}")
    public ResponseEntity<ApiResponse<List<ProposalClauseResponseDto>>> getClauses(@PathVariable Long companyId) {
        List<ProposalClauseResponseDto> clauses = proposalService.getClausesByCompany(companyId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal clauses fetched successfully", clauses));
    }

    @PutMapping("/clauses/{clauseId}")
    public ResponseEntity<ApiResponse<ProposalClauseResponseDto>> updateClause(
            @PathVariable Long clauseId,
            @RequestBody Map<String, String> body) {

        String newContent = body.get("clauseContent");
        ProposalClauseResponseDto updatedClause = proposalService.updateClause(clauseId, newContent);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal clause updated successfully", updatedClause));
    }

    @DeleteMapping("/clauses/{clauseId}")
    public ResponseEntity<ApiResponse<Void>> deleteClause(@PathVariable Long clauseId) {
        proposalService.deleteClause(clauseId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal clause deleted successfully", null));
    }

    @PutMapping("/{companyId}/accept")
    public ResponseEntity<ApiResponse<Void>> acceptProposal(@PathVariable Long companyId) {
        proposalService.acceptProposal(companyId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal accepted successfully. Ready for quotation.", null));
    }

    @PutMapping("/{companyId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectProposal(@PathVariable Long companyId) {
        proposalService.rejectProposal(companyId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposal rejected. Workflow terminated.", null));
    }


}