package com.exponab.invoice.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "material_receiving")
public class MaterialReceiving {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private Long purchaseOrderId;

    private String receivedDate;

    private String status;

    private String invoiceNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_receiving_id")
    private List<MaterialReceivingItem> items;


    public MaterialReceiving() {
		// TODO Auto-generated constructor stub
	}


	public MaterialReceiving(Long id, Long companyId, Long purchaseOrderId, String receivedDate, String status,
			String invoiceNumber, List<MaterialReceivingItem> items) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.purchaseOrderId = purchaseOrderId;
		this.receivedDate = receivedDate;
		this.status = status;
		this.invoiceNumber = invoiceNumber;
		this.items = items;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}


	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}


	public String getReceivedDate() {
		return receivedDate;
	}


	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public List<MaterialReceivingItem> getItems() {
		return items;
	}


	public void setItems(List<MaterialReceivingItem> items) {
		this.items = items;
	}
    
    
    
}
