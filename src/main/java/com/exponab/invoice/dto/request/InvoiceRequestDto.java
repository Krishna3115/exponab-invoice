package com.exponab.invoice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;



public class InvoiceRequestDto {
    
    @NotNull(message = "Company ID is required")
    private Long companyId;
    
    private BigDecimal taxPercent;
    private BigDecimal discountPercent;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    
    
    public InvoiceRequestDto() {
		// TODO Auto-generated constructor stub
	}


	public InvoiceRequestDto(@NotNull(message = "Company ID is required") Long companyId, BigDecimal taxPercent,
			BigDecimal discountPercent, LocalDate dueDate) {
		super();
		this.companyId = companyId;
		this.taxPercent = taxPercent;
		this.discountPercent = discountPercent;
		this.dueDate = dueDate;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public BigDecimal getTaxPercent() {
		return taxPercent;
	}


	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}


	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}


	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
    
    
    
    
    
}