package com.exponab.invoice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SalesReportDetailsResponseDto {

	 private Long id;
	    private String reportNumber;
	    private LocalDate reportDate;

	    private Long customerId;
	    private String customerName;
	    private String customerEmail;

	    private String procurementType;
	    private String taxMode;

	    private BigDecimal subtotal;
	    private BigDecimal taxAmount;
	    private BigDecimal grandTotal;

	    private String notes;

	    private Long purchaseOrderId;

	    private List<SalesReportItemResponseDto> items;
	    private List<SalesReportExpenseResponseDto> expenses;
	
	    private String containerNumber;
	    
	    public SalesReportDetailsResponseDto() {
			// TODO Auto-generated constructor stub
		}

		public SalesReportDetailsResponseDto(Long id, String reportNumber, LocalDate reportDate, Long customerId,
				String customerName, String customerEmail, String procurementType, String taxMode, BigDecimal subtotal,
				BigDecimal taxAmount, BigDecimal grandTotal, String notes, Long purchaseOrderId,
				List<SalesReportItemResponseDto> items, List<SalesReportExpenseResponseDto> expenses) {
			super();
			this.id = id;
			this.reportNumber = reportNumber;
			this.reportDate = reportDate;
			this.customerId = customerId;
			this.customerName = customerName;
			this.customerEmail = customerEmail;
			this.procurementType = procurementType;
			this.taxMode = taxMode;
			this.subtotal = subtotal;
			this.taxAmount = taxAmount;
			this.grandTotal = grandTotal;
			this.notes = notes;
			this.purchaseOrderId = purchaseOrderId;
			this.items = items;
			this.expenses = expenses;
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

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getCustomerEmail() {
			return customerEmail;
		}

		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
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

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Long getPurchaseOrderId() {
			return purchaseOrderId;
		}

		public void setPurchaseOrderId(Long purchaseOrderId) {
			this.purchaseOrderId = purchaseOrderId;
		}

		public List<SalesReportItemResponseDto> getItems() {
			return items;
		}

		public void setItems(List<SalesReportItemResponseDto> items) {
			this.items = items;
		}

		public List<SalesReportExpenseResponseDto> getExpenses() {
			return expenses;
		}

		public void setExpenses(List<SalesReportExpenseResponseDto> expenses) {
			this.expenses = expenses;
		}

		public String getContainerNumber() {
			return containerNumber;
		}

		public void setContainerNumber(String containerNumber) {
			this.containerNumber = containerNumber;
		}
	    
	    
	    
}
