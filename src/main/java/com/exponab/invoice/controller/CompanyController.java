package com.exponab.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exponab.invoice.dto.request.CompanyRequestDto;
import com.exponab.invoice.dto.response.ApiResponse;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.service.CompanyService;

import jakarta.validation.Valid;



// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/companies")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Company>> registerCompany(@Valid @RequestBody CompanyRequestDto dto) {
        Company saved = companyService.registerCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Company registered successfully", saved));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Companies fetched successfully", companyService.getAllCompanies()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Company fetched successfully", companyService.getCompanyById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDto dto) {
        Company updated = companyService.updateCompany(id, dto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Company updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Company deleted successfully", null));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Company>>> getByStatus(@PathVariable CompanyStatus status) {
        List<Company> companies = companyService.getCompaniesByStatus(status);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Companies fetched successfully", companies));
    }

//    @PutMapping("/{id}/sign-agreement")
//    public ResponseEntity<ApiResponse<Company>> markAgreementAsSigned(@PathVariable Long id) {
//        Company updatedCompany = companyService.markAgreementSigned(id);
//        return ResponseEntity.ok(
//                new ApiResponse<>(true, "Agreement marked as signed successfully", updatedCompany));
//    }
}