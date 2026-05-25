package com.exponab.invoice.dto.response;

import java.math.BigDecimal;

public class SalesReportItemResponseDto {

	private String articleName;
    private String description;

    private BigDecimal quantity;
    private BigDecimal unitPrice;

    private BigDecimal vatPercent;
    private BigDecimal vatAmount;

    private BigDecimal totalPrice;
    
	public SalesReportItemResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public SalesReportItemResponseDto(String articleName, String description, BigDecimal quantity, BigDecimal unitPrice,
			BigDecimal vatPercent, BigDecimal vatAmount, BigDecimal totalPrice) {
		super();
		this.articleName = articleName;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.vatPercent = vatPercent;
		this.vatAmount = vatAmount;
		this.totalPrice = totalPrice;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(BigDecimal vatPercent) {
		this.vatPercent = vatPercent;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	


}
