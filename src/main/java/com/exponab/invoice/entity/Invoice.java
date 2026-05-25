package com.exponab.invoice.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber; // e.g., INV-2026-001
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDate dueDate;
    
    private BigDecimal taxPercent;
    private BigDecimal discountPercent;
    
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    
    
    public Invoice() {
		// TODO Auto-generated constructor stub
	}
    

    public Invoice(Long id, String invoiceNumber, Company company, List<InvoiceItem> items, LocalDateTime createdAt,
			LocalDate dueDate, BigDecimal taxPercent, BigDecimal discountPercent, BigDecimal subtotal,
			BigDecimal discountAmount, BigDecimal taxAmount, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.company = company;
		this.items = items;
		this.createdAt = createdAt;
		this.dueDate = dueDate;
		this.taxPercent = taxPercent;
		this.discountPercent = discountPercent;
		this.subtotal = subtotal;
		this.discountAmount = discountAmount;
		this.taxAmount = taxAmount;
		this.totalAmount = totalAmount;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getInvoiceNumber() {
		return invoiceNumber;
	}



	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}



	public Company getCompany() {
		return company;
	}



	public void setCompany(Company company) {
		this.company = company;
	}



	public List<InvoiceItem> getItems() {
		return items;
	}



	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDate getDueDate() {
		return dueDate;
	}



	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}



	public BigDecimal getTaxPercent() {
		return taxPercent;
	}



	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}



	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}



	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}



	public BigDecimal getSubtotal() {
		return subtotal;
	}



	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}



	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}



	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}



	public BigDecimal getTaxAmount() {
		return taxAmount;
	}



	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}



	public BigDecimal getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}



	public void calculateFinancials() {

	    // 1. Subtotal (Sum of all item prices)
	    this.subtotal = items.stream()
	            .map(item -> item.getPrice())
	            .filter(Objects::nonNull)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);

	    // 2. Discount Amount
	    this.discountAmount = subtotal.multiply(discountPercent)
	            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

	    BigDecimal afterDiscount = subtotal.subtract(discountAmount);

	    // 3. Tax Amount
	    this.taxAmount = afterDiscount.multiply(taxPercent)
	            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

	    // 4. Final Total
	    this.totalAmount = afterDiscount.add(taxAmount);
	}

    public void generateInvoiceNumber() {
        if (this.invoiceNumber == null) {
            // Format: INV-2026-A1B2C3
            String year = String.valueOf(LocalDate.now().getYear());
            String uniqueID = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            
            this.invoiceNumber = String.format("INV-%s-%s", year, uniqueID);
        }
    }

    @PrePersist
    public void prePersistLogic() {
        generateInvoiceNumber();
        calculateFinancials();
        if (this.dueDate == null) {
            this.dueDate = LocalDate.now().plusDays(30);
        }
    }
}
