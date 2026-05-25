package com.exponab.invoice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter

public class PurchaseOrderResponseDto {

	 private Long id;
	    private String poNumber;
	    private LocalDate poDate;

	    private String exporterName;

	    private BigDecimal grandTotal;

	    private String status;

	    public PurchaseOrderResponseDto() {
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderResponseDto(Long id, String poNumber, LocalDate poDate, String exporterName,
				BigDecimal grandTotal, String status) {
			super();
			this.id = id;
			this.poNumber = poNumber;
			this.poDate = poDate;
			this.exporterName = exporterName;
			this.grandTotal = grandTotal;
			this.status = status;
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

		public String getExporterName() {
			return exporterName;
		}

		public void setExporterName(String exporterName) {
			this.exporterName = exporterName;
		}

		public BigDecimal getGrandTotal() {
			return grandTotal;
		}

		public void setGrandTotal(BigDecimal grandTotal) {
			this.grandTotal = grandTotal;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	    
		
	    
		
	}
