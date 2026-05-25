package com.exponab.invoice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class QuotationResponseDto {
    private Long id;
    private String quotationNumber;
    
    // Flattened Company Info (Frontend rarely needs the whole company object here)
    private Long companyId;
    private String companyName;

    private List<QuotationItemResponseDto> items;

    private BigDecimal discountPercent;
    private BigDecimal taxPercent;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    
    private LocalDateTime createdAt;
    
    public QuotationResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public QuotationResponseDto(Long id, String quotationNumber, Long companyId, String companyName,
			List<QuotationItemResponseDto> items, BigDecimal discountPercent, BigDecimal taxPercent,
			BigDecimal subtotal, BigDecimal discountAmount, BigDecimal taxAmount, BigDecimal totalAmount,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.quotationNumber = quotationNumber;
		this.companyId = companyId;
		this.companyName = companyName;
		this.items = items;
		this.discountPercent = discountPercent;
		this.taxPercent = taxPercent;
		this.subtotal = subtotal;
		this.discountAmount = discountAmount;
		this.taxAmount = taxAmount;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<QuotationItemResponseDto> getItems() {
		return items;
	}

	public void setItems(List<QuotationItemResponseDto> items) {
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}