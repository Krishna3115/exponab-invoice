package com.exponab.invoice.dto.response;

import java.math.BigDecimal;

public class SalesReportExpenseDto {

	 private Long expenseMasterId;

	 private String description;
	 
	    private BigDecimal quantity;

	    private BigDecimal unitRate;

	    public SalesReportExpenseDto() {
	    }

		public SalesReportExpenseDto(Long expenseMasterId, String description, BigDecimal quantity,
				BigDecimal unitRate) {
			super();
			this.expenseMasterId = expenseMasterId;
			this.description = description;
			this.quantity = quantity;
			this.unitRate = unitRate;
		}

		public Long getExpenseMasterId() {
			return expenseMasterId;
		}

		public void setExpenseMasterId(Long expenseMasterId) {
			this.expenseMasterId = expenseMasterId;
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

		public BigDecimal getUnitRate() {
			return unitRate;
		}

		public void setUnitRate(BigDecimal unitRate) {
			this.unitRate = unitRate;
		}

	      	    
}
