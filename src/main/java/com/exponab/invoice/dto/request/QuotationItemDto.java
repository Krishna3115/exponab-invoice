package com.exponab.invoice.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class QuotationItemDto {

    @NotBlank(message = "Service description is required")
    private String serviceDescription;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    public QuotationItemDto() {
		// TODO Auto-generated constructor stub
	}

	public QuotationItemDto(@NotBlank(message = "Service description is required") String serviceDescription,
			@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price) {
		super();
		this.serviceDescription = serviceDescription;
		this.price = price;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    
    // Getters and Setters
}
