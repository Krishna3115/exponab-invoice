package com.exponab.invoice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exponab.invoice.entity.SalesReport;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, Long>{

	@Query("SELECT s FROM SalesReport s LEFT JOIN FETCH s.expenses WHERE s.id = :id")
    Optional<SalesReport> findByIdWithExpenses(@Param("id") Long id);

}
