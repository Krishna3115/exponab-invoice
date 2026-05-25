package com.exponab.invoice.dto.request;

import java.math.BigDecimal;

public class SalesReportItemDto {

	private String articleName;

    private String description;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal vatPercent;

    public SalesReportItemDto() {
    }

	public SalesReportItemDto(String articleName, String description, BigDecimal quantity, BigDecimal unitPrice,
			BigDecimal vatPercent) {
		super();
		this.articleName = articleName;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.vatPercent = vatPercent;
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
    
    
}
