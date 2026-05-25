package com.exponab.invoice.dto.request;

import java.math.BigDecimal;

public class ExpenseMasterRequestDto {

	private String expenseName;

    private BigDecimal defaultQuantity;

    private BigDecimal defaultRate;
    
    public ExpenseMasterRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public ExpenseMasterRequestDto(String expenseName, BigDecimal defaultQuantity, BigDecimal defaultRate) {
		super();
		this.expenseName = expenseName;
		this.defaultQuantity = defaultQuantity;
		this.defaultRate = defaultRate;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public BigDecimal getDefaultQuantity() {
		return defaultQuantity;
	}

	public void setDefaultQuantity(BigDecimal defaultQuantity) {
		this.defaultQuantity = defaultQuantity;
	}

	public BigDecimal getDefaultRate() {
		return defaultRate;
	}

	public void setDefaultRate(BigDecimal defaultRate) {
		this.defaultRate = defaultRate;
	}
    
    
}
