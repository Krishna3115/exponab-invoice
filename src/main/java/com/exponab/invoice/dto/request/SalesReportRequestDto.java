package com.exponab.invoice.dto.request;

import java.util.List;

import com.exponab.invoice.dto.response.SalesReportExpenseDto;
import com.exponab.invoice.entity.ProcurementType;
import com.exponab.invoice.entity.TaxMode;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SalesReportRequestDto {

	@NotNull
	 	private Long customerId;

	@NotNull
	    private ProcurementType procurementType;

	    // OPTIONAL
	    private Long purchaseOrderId;

	    @NotNull
	    private TaxMode taxMode;

	    private String notes;

	    @NotEmpty
	    private List<SalesReportItemDto> items;

	    private List<SalesReportExpenseDto> expenses;
	    
	    public SalesReportRequestDto() {
	    }

		public SalesReportRequestDto(Long customerId, ProcurementType procurementType, Long purchaseOrderId,
				TaxMode taxMode, String notes, List<SalesReportItemDto> items, List<SalesReportExpenseDto> expenses) {
			super();
			this.customerId = customerId;
			this.procurementType = procurementType;
			this.purchaseOrderId = purchaseOrderId;
			this.taxMode = taxMode;
			this.notes = notes;
			this.items = items;
			this.expenses = expenses;
		}

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public ProcurementType getProcurementType() {
			return procurementType;
		}

		public void setProcurementType(ProcurementType procurementType) {
			this.procurementType = procurementType;
		}

		public Long getPurchaseOrderId() {
			return purchaseOrderId;
		}

		public void setPurchaseOrderId(Long purchaseOrderId) {
			this.purchaseOrderId = purchaseOrderId;
		}

		public TaxMode getTaxMode() {
			return taxMode;
		}

		public void setTaxMode(TaxMode taxMode) {
			this.taxMode = taxMode;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public List<SalesReportItemDto> getItems() {
			return items;
		}

		public void setItems(List<SalesReportItemDto> items) {
			this.items = items;
		}

		public List<SalesReportExpenseDto> getExpenses() {
			return expenses;
		}

		public void setExpenses(List<SalesReportExpenseDto> expenses) {
			this.expenses = expenses;
		}

		
	    
	    
}
