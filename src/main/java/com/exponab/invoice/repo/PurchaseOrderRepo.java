package com.exponab.invoice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exponab.invoice.entity.PurchaseOrder;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long>{

	
}
