package com.exponab.invoice.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PurchaseOrderItemDto {

	private String commodity;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    
    public PurchaseOrderItemDto() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrderItemDto(String commodity, BigDecimal quantity, String unit, BigDecimal unitPrice) {
		super();
		this.commodity = commodity;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
    
    
}
