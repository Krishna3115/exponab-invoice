package com.exponab.invoice.dto.request;

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

	    private List<PurchaseOrderItemDto> items;
	    
	    public PurchaseOrderRequestDto() {
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderRequestDto(Long exporterId, String countryOfOrigin, String destinationPort,
				String incoterms, String transportMode, String qualityStandard, String paymentTerms,
				String deliveryTerms, List<PurchaseOrderItemDto> items) {
			super();
			this.exporterId = exporterId;
			this.countryOfOrigin = countryOfOrigin;
			this.destinationPort = destinationPort;
			this.incoterms = incoterms;
			this.transportMode = transportMode;
			this.qualityStandard = qualityStandard;
			this.paymentTerms = paymentTerms;
			this.deliveryTerms = deliveryTerms;
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

		public List<PurchaseOrderItemDto> getItems() {
			return items;
		}

		public void setItems(List<PurchaseOrderItemDto> items) {
			this.items = items;
		}
	    
	    
	    
	}