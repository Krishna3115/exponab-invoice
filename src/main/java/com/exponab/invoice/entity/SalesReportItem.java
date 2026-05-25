package com.exponab.invoice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_report_items")
public class SalesReportItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_report_id")
    private SalesReport salesReport;

    private String articleName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal vatPercent;

    private BigDecimal vatAmount;

    private BigDecimal totalPrice;


    public SalesReportItem() {
    
    }


	public SalesReportItem(Long id, SalesReport salesReport, String articleName, String description,
			BigDecimal quantity, BigDecimal unitPrice, BigDecimal vatPercent, BigDecimal vatAmount,
			BigDecimal totalPrice) {
		super();
		this.id = id;
		this.salesReport = salesReport;
		this.articleName = articleName;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.vatPercent = vatPercent;
		this.vatAmount = vatAmount;
		this.totalPrice = totalPrice;
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


	public BigDecimal getVatAmount() {
		return vatAmount;
	}


	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    
}
