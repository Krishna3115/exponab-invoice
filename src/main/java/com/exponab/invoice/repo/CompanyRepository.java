package com.exponab.invoice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);
    boolean existsByGstNumber(String gstNumber);
    Optional<Company> findByEmail(String email);
    List<Company> findByStatus(CompanyStatus status);
}