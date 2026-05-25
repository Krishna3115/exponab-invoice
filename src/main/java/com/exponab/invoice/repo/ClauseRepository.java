package com.exponab.invoice.repo;

import com.exponab.invoice.entity.Clause;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClauseRepository extends JpaRepository<Clause, Long> {
    // Custom query to delete old draft clauses by company ID
    void deleteByCompanyId(Long companyId);
    List<Clause> findByCompanyId(Long companyId);
}