package com.exponab.invoice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exponab.invoice.entity.ExpenseMaster;

public interface ExpenseMasterRepository extends JpaRepository<ExpenseMaster, Long>{

}
