package com.exponab.invoice.dto.response;

import java.math.BigDecimal;

public class SalesReportExpenseResponseDto {


    private Long expenseMasterId;
    private String expenseName;

    private BigDecimal quantity;
    private BigDecimal unitRate;
    private BigDecimal amount;
    
    public SalesReportExpenseResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public SalesReportExpenseResponseDto(Long expenseMasterId, String expenseName, BigDecimal quantity,
			BigDecimal unitRate, BigDecimal amount) {
		super();
		this.expenseMasterId = expenseMasterId;
		this.expenseName = expenseName;
		this.quantity = quantity;
		this.unitRate = unitRate;
		this.amount = amount;
	}

	public Long getExpenseMasterId() {
		return expenseMasterId;
	}

	public void setExpenseMasterId(Long expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(BigDecimal unitRate) {
		this.unitRate = unitRate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
    
    
}
