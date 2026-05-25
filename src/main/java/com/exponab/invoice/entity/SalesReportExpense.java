package com.exponab.invoice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_report_expenses")
public class SalesReportExpense {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // WHICH SALES REPORT
	    @ManyToOne
	    @JoinColumn(name = "sales_report_id")
	    private SalesReport salesReport;

	    // WHICH EXPENSE MASTER
	    @ManyToOne
	    @JoinColumn(name = "expense_master_id")
	    private ExpenseMaster expenseMaster;

	    // EXPENSE NAME
	    private String description;

	    // QUANTITY
	    private BigDecimal quantity;

	    // RATE
	    private BigDecimal unitRate;

	    // TOTAL
	    private BigDecimal amount;
	    
	    
	    public SalesReportExpense() {
			// TODO Auto-generated constructor stub
		}


		public SalesReportExpense(Long id, SalesReport salesReport, ExpenseMaster expenseMaster, String description,
				BigDecimal quantity, BigDecimal unitRate, BigDecimal amount) {
			super();
			this.id = id;
			this.salesReport = salesReport;
			this.expenseMaster = expenseMaster;
			this.description = description;
			this.quantity = quantity;
			this.unitRate = unitRate;
			this.amount = amount;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public SalesReport getSalesReport() {
			return salesReport;
		}


		public void setSalesReport(SalesReport salesReport) {
			this.salesReport = salesReport;
		}


		public ExpenseMaster getExpenseMaster() {
			return expenseMaster;
		}


		public void setExpenseMaster(ExpenseMaster expenseMaster) {
			this.expenseMaster = expenseMaster;
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


		public BigDecimal getAmount() {
			return amount;
		}


		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
	    
	    
}
