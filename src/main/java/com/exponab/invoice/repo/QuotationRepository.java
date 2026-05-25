package com.exponab.invoice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exponab.invoice.entity.Quotation;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    List<Quotation> findByCompanyId(Long companyId);
    Optional<Quotation> findByQuotationNumber(String quotationNumber);
    Optional<Quotation> findFirstByCompanyIdOrderByCreatedAtDesc(Long companyId);
}