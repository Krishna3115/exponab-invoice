package com.exponab.invoice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exponab.invoice.entity.SalesReportExpense;

public interface SalesReportExpenseRepository extends JpaRepository<SalesReportExpense, Long>{

}
