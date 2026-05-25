package com.exponab.invoice.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class QuotationRequestDto {

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotEmpty(message = "At least one item is required")
    private List<QuotationItemDto> items;

    // Optional overrides — defaults applied via constants if not provided
    private BigDecimal discountPercent;
    private BigDecimal taxPercent;
    
    
    public QuotationRequestDto() {
		// TODO Auto-generated constructor stub
	}


	public QuotationRequestDto(@NotNull(message = "Company ID is required") Long companyId,
			@NotEmpty(message = "At least one item is required") List<QuotationItemDto> items,
			BigDecimal discountPercent, BigDecimal taxPercent) {
		super();
		this.companyId = companyId;
		this.items = items;
		this.discountPercent = discountPercent;
		this.taxPercent = taxPercent;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public List<QuotationItemDto> getItems() {
		return items;
	}


	public void setItems(List<QuotationItemDto> items) {
		this.items = items;
	}


	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}


	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}


	public BigDecimal getTaxPercent() {
		return taxPercent;
	}


	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}
    
    
    
}