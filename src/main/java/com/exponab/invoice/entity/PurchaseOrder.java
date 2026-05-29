package com.exponab.invoice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
public class PurchaseOrder {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String poNumber;

    private LocalDate poDate;

    // CLIENT (Exporter)
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Company exporter;

    // IMPORTER (static system company name)
    private String importerName;

    // Shipment details
    private String countryOfOrigin;
    private String destinationPort;
    private String incoterms;
    private String transportMode;

    // Terms
    @Column(columnDefinition = "TEXT")
    private String qualityStandard;

    @Column(columnDefinition = "TEXT")
    private String paymentTerms;

    @Column(columnDefinition = "TEXT")
    private String deliveryTerms;

    private BigDecimal grandTotal;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(length = 100)
    private String commodity;

    @Column(length = 200)
    private String quality;

    @Column(length = 200)
    private String packaging;

    @Column(length = 50)
    private String totalQuantity;   // e.g. "28500 KG"

    @Column(length = 100)
    private String priceNote;       // e.g. "As per Invoice"

    @Column(length = 10)
    private String currency;        // USD / AED / INR / EUR

    @Column(precision = 10, scale = 4)
    private java.math.BigDecimal exchangeRate;  // e.g. 3.6730 (USD→AED), nullable
    
public PurchaseOrder() {
	// TODO Auto-generated constructor stub
}

public PurchaseOrder(Long id, String poNumber, LocalDate poDate, Company exporter, String importerName,
		String countryOfOrigin, String destinationPort, String incoterms, String transportMode, String qualityStandard,
		String paymentTerms, String deliveryTerms, BigDecimal grandTotal, PurchaseOrderStatus status,
		List<PurchaseOrderItem> items, LocalDateTime createdAt, String commodity, String quality, String packaging,
		String totalQuantity, String priceNote, String currency, BigDecimal exchangeRate) {
	super();
	this.id = id;
	this.poNumber = poNumber;
	this.poDate = poDate;
	this.exporter = exporter;
	this.importerName = importerName;
	this.countryOfOrigin = countryOfOrigin;
	this.destinationPort = destinationPort;
	this.incoterms = incoterms;
	this.transportMode = transportMode;
	this.qualityStandard = qualityStandard;
	this.paymentTerms = paymentTerms;
	this.deliveryTerms = deliveryTerms;
	this.grandTotal = grandTotal;
	this.status = status;
	this.items = items;
	this.createdAt = createdAt;
	this.commodity = commodity;
	this.quality = quality;
	this.packaging = packaging;
	this.totalQuantity = totalQuantity;
	this.priceNote = priceNote;
	this.currency = currency;
	this.exchangeRate = exchangeRate;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getPoNumber() {
	return poNumber;
}

public void setPoNumber(String poNumber) {
	this.poNumber = poNumber;
}

public LocalDate getPoDate() {
	return poDate;
}

public void setPoDate(LocalDate poDate) {
	this.poDate = poDate;
}

public Company getExporter() {
	return exporter;
}

public void setExporter(Company exporter) {
	this.exporter = exporter;
}

public String getImporterName() {
	return importerName;
}

public void setImporterName(String importerName) {
	this.importerName = importerName;
}

public String getCountryOfOrigin() {
	return countryOfOrigin;
}

public void setCountryOfOrigin(String countryOfOrigin) {
	this.countryOfOrigin = countryOfOrigin;
}

public String getDestinationPort() {
	return destinationPort;
}

public void setDestinationPort(String destinationPort) {
	this.destinationPort = destinationPort;
}

public String getIncoterms() {
	return incoterms;
}

public void setIncoterms(String incoterms) {
	this.incoterms = incoterms;
}

public String getTransportMode() {
	return transportMode;
}

public void setTransportMode(String transportMode) {
	this.transportMode = transportMode;
}

public String getQualityStandard() {
	return qualityStandard;
}

public void setQualityStandard(String qualityStandard) {
	this.qualityStandard = qualityStandard;
}

public String getPaymentTerms() {
	return paymentTerms;
}

public void setPaymentTerms(String paymentTerms) {
	this.paymentTerms = paymentTerms;
}

public String getDeliveryTerms() {
	return deliveryTerms;
}

public void setDeliveryTerms(String deliveryTerms) {
	this.deliveryTerms = deliveryTerms;
}

public BigDecimal getGrandTotal() {
	return grandTotal;
}

public void setGrandTotal(BigDecimal grandTotal) {
	this.grandTotal = grandTotal;
}

public PurchaseOrderStatus getStatus() {
	return status;
}

public void setStatus(PurchaseOrderStatus status) {
	this.status = status;
}

public List<PurchaseOrderItem> getItems() {
	return items;
}

public void setItems(List<PurchaseOrderItem> items) {
	this.items = items;
}

public LocalDateTime getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}

public String getCommodity() {
	return commodity;
}

public void setCommodity(String commodity) {
	this.commodity = commodity;
}

public String getQuality() {
	return quality;
}

public void setQuality(String quality) {
	this.quality = quality;
}

public String getPackaging() {
	return packaging;
}

public void setPackaging(String packaging) {
	this.packaging = packaging;
}

public String getTotalQuantity() {
	return totalQuantity;
}

public void setTotalQuantity(String totalQuantity) {
	this.totalQuantity = totalQuantity;
}

public String getPriceNote() {
	return priceNote;
}

public void setPriceNote(String priceNote) {
	this.priceNote = priceNote;
}

public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

public java.math.BigDecimal getExchangeRate() {
	return exchangeRate;
}

public void setExchangeRate(java.math.BigDecimal exchangeRate) {
	this.exchangeRate = exchangeRate;
}


	
}
