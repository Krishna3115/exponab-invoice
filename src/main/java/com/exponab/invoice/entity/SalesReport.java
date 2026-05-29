package com.exponab.invoice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_reports")
public class SalesReport {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String reportNumber;

	    private LocalDate reportDate;

	    // CUSTOMER
	    @ManyToOne
	    @JoinColumn(name = "company_id")
	    private Company customer;

	    // WITH PO / WITHOUT PO
	    @Enumerated(EnumType.STRING)
	    private ProcurementType procurementType;

	    // OPTIONAL PO
	    @ManyToOne
	    @JoinColumn(name = "purchase_order_id")
	    private PurchaseOrder purchaseOrder;

	    // VAT MODE
	    @Enumerated(EnumType.STRING)
	    private TaxMode taxMode;

	    private BigDecimal subtotal;

	    private BigDecimal taxAmount;

	    private BigDecimal grandTotal;

	    @Column(columnDefinition = "TEXT")
	    private String notes;

	    @OneToMany(mappedBy = "salesReport",
	            cascade = CascadeType.ALL)
	    private List<SalesReportItem> items = new ArrayList<>();
	    
	    @OneToMany(
	            mappedBy = "salesReport",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true
	    )
	    private List<SalesReportExpense> expenses
	            = new ArrayList<>();
	    
	    private BigDecimal expenseTotal;

	    private BigDecimal finalPayable;
	    
	    private String currency;

	    private String currencySymbol;
	    
	    @Column(name = "container_number", nullable = false, length = 100)
	    private String containerNumber;
	    
	    public SalesReport() {
			// TODO Auto-generated constructor stub
		}

		public SalesReport(Long id, String reportNumber, LocalDate reportDate, Company customer,
				ProcurementType procurementType, PurchaseOrder purchaseOrder, TaxMode taxMode, BigDecimal subtotal,
				BigDecimal taxAmount, BigDecimal grandTotal, String notes, List<SalesReportItem> items,
				List<SalesReportExpense> expenses, BigDecimal expenseTotal, BigDecimal finalPayable, String currency,
				String currencySymbol, String containerNumber) {
			super();
			this.id = id;
			this.reportNumber = reportNumber;
			this.reportDate = reportDate;
			this.customer = customer;
			this.procurementType = procurementType;
			this.purchaseOrder = purchaseOrder;
			this.taxMode = taxMode;
			this.subtotal = subtotal;
			this.taxAmount = taxAmount;
			this.grandTotal = grandTotal;
			this.notes = notes;
			this.items = items;
			this.expenses = expenses;
			this.expenseTotal = expenseTotal;
			this.finalPayable = finalPayable;
			this.currency = currency;
			this.currencySymbol = currencySymbol;
			this.containerNumber = containerNumber;
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

		public Company getCustomer() {
			return customer;
		}

		public void setCustomer(Company customer) {
			this.customer = customer;
		}

		public ProcurementType getProcurementType() {
			return procurementType;
		}

		public void setProcurementType(ProcurementType procurementType) {
			this.procurementType = procurementType;
		}

		public PurchaseOrder getPurchaseOrder() {
			return purchaseOrder;
		}

		public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
			this.purchaseOrder = purchaseOrder;
		}

		public TaxMode getTaxMode() {
			return taxMode;
		}

		public void setTaxMode(TaxMode taxMode) {
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

		public List<SalesReportItem> getItems() {
			return items;
		}

		public void setItems(List<SalesReportItem> items) {
			this.items = items;
		}

		public List<SalesReportExpense> getExpenses() {
			return expenses;
		}

		public void setExpenses(List<SalesReportExpense> expenses) {
			this.expenses = expenses;
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

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getCurrencySymbol() {
			return currencySymbol;
		}

		public void setCurrencySymbol(String currencySymbol) {
			this.currencySymbol = currencySymbol;
		}

		public String getContainerNumber() {
			return containerNumber;
		}

		public void setContainerNumber(String containerNumber) {
			this.containerNumber = containerNumber;
		}

		
}
