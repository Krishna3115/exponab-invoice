package com.exponab.invoice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense_master")
public class ExpenseMaster {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expenseName;

    private BigDecimal defaultQuantity;

    private BigDecimal defaultRate;

    private Boolean active = true;
    
    public ExpenseMaster() {
		// TODO Auto-generated constructor stub
	}

	public ExpenseMaster(Long id, String expenseName, BigDecimal defaultQuantity, BigDecimal defaultRate,
			Boolean active) {
		super();
		this.id = id;
		this.expenseName = expenseName;
		this.defaultQuantity = defaultQuantity;
		this.defaultRate = defaultRate;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
    
    
    
}
