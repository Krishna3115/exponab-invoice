package com.exponab.invoice.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrderRequestDto {

	 private Long exporterId;

	    private String countryOfOrigin;
	    private String destinationPort;
	    private String incoterms;
	    private String transportMode;

	    private String qualityStandard;
	    private String paymentTerms;
	    private String deliveryTerms;
	    
	    private String commodity;
	    private String quality;
	    private String packaging;
	    private String totalQuantity;
	    private String priceNote;
	    private String currency;
	    private java.math.BigDecimal exchangeRate;

	    private List<PurchaseOrderItemDto> items;
	    
	    public PurchaseOrderRequestDto() {
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderRequestDto(Long exporterId, String countryOfOrigin, String destinationPort,
				String incoterms, String transportMode, String qualityStandard, String paymentTerms,
				String deliveryTerms, String commodity, String quality, String packaging, String totalQuantity,
				String priceNote, String currency, BigDecimal exchangeRate, List<PurchaseOrderItemDto> items) {
			super();
			this.exporterId = exporterId;
			this.countryOfOrigin = countryOfOrigin;
			this.destinationPort = destinationPort;
			this.incoterms = incoterms;
			this.transportMode = transportMode;
			this.qualityStandard = qualityStandard;
			this.paymentTerms = paymentTerms;
			this.deliveryTerms = deliveryTerms;
			this.commodity = commodity;
			this.quality = quality;
			this.packaging = packaging;
			this.totalQuantity = totalQuantity;
			this.priceNote = priceNote;
			this.currency = currency;
			this.exchangeRate = exchangeRate;
			this.items = items;
		}

		public Long getExporterId() {
			return exporterId;
		}

		public void setExporterId(Long exporterId) {
			this.exporterId = exporterId;
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

		public List<PurchaseOrderItemDto> getItems() {
			return items;
		}

		public void setItems(List<PurchaseOrderItemDto> items) {
			this.items = items;
		}

		
	    
	}