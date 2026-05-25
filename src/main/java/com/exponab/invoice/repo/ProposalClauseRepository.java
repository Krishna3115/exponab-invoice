package com.exponab.invoice.repo;

import com.exponab.invoice.entity.ProposalClause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalClauseRepository extends JpaRepository<ProposalClause, Long> {
    List<ProposalClause> findByCompanyId(Long companyId);
}