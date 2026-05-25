package com.exponab.invoice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesReportResponseDto {

	  private Long id;

	    private String reportNumber;

	    private LocalDate reportDate;

	    private String customerName;

	    private String procurementType;

	    private String taxMode;

	    private BigDecimal subtotal;

	    private BigDecimal taxAmount;

	    private BigDecimal grandTotal;

	    // NEW
	    private BigDecimal expenseTotal;

	    // NEW
	    private BigDecimal finalPayable;
	    
	    public SalesReportResponseDto() {
			// TODO Auto-generated constructor stub
		}

		public SalesReportResponseDto(Long id, String reportNumber, LocalDate reportDate, String customerName,
				String procurementType, String taxMode, BigDecimal subtotal, BigDecimal taxAmount,
				BigDecimal grandTotal, BigDecimal expenseTotal, BigDecimal finalPayable) {
			super();
			this.id = id;
			this.reportNumber = reportNumber;
			this.reportDate = reportDate;
			this.customerName = customerName;
			this.procurementType = procurementType;
			this.taxMode = taxMode;
			this.subtotal = subtotal;
			this.taxAmount = taxAmount;
			this.grandTotal = grandTotal;
			this.expenseTotal = expenseTotal;
			this.finalPayable = finalPayable;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getReportNumber() {
			return reportNumber;
		}

		public void setReportNumber(String reportNumber) {
			this.reportNumber = reportNumber;
		}

		public LocalDate getReportDate() {
			return reportDate;
		}

		public void setReportDate(LocalDate reportDate) {
			this.reportDate = reportDate;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getProcurementType() {
			return procurementType;
		}

		public void setProcurementType(String procurementType) {
			this.procurementType = procurementType;
		}

		public String getTaxMode() {
			return taxMode;
		}

		public void setTaxMode(String taxMode) {
			this.taxMode = taxMode;
		}

		public BigDecimal getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(BigDecimal subtotal) {
			this.subtotal = subtotal;
		}

		public BigDecimal getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(BigDecimal taxAmount) {
			this.taxAmount = taxAmount;
		}

		public BigDecimal getGrandTotal() {
			return grandTotal;
		}

		public void setGrandTotal(BigDecimal grandTotal) {
			this.grandTotal = grandTotal;
		}

		public BigDecimal getExpenseTotal() {
			return expenseTotal;
		}

		public void setExpenseTotal(BigDecimal expenseTotal) {
			this.expenseTotal = expenseTotal;
		}

		public BigDecimal getFinalPayable() {
			return finalPayable;
		}

		public void setFinalPayable(BigDecimal finalPayable) {
			this.finalPayable = finalPayable;
		}

		
}
