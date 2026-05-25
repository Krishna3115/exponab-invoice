package com.exponab.invoice.dto.request;

import java.util.List;

public class MaterialReceivingRequestDto {

    private Long companyId;
    private Long purchaseOrderId;
    private String receivedDate;
    private String status;
    private String invoiceNumber;

    private List<ItemDto> items;

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(Long purchaseOrderId) { this.purchaseOrderId = purchaseOrderId; }

    public String getReceivedDate() { return receivedDate; }
    public void setReceivedDate(String receivedDate) { this.receivedDate = receivedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }

    // INNER CLASS
    public static class ItemDto {

        private String description;
        private Integer quantity;
        private Double rate;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public Double getRate() { return rate; }
        public void setRate(Double rate) { this.rate = rate; }
    }
}