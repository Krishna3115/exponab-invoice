package com.exponab.invoice.dto.response;

import java.math.BigDecimal;


public class QuotationItemResponseDto {
    private Long id;
    private String serviceDescription;
    private BigDecimal price;
    
    public QuotationItemResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public QuotationItemResponseDto(Long id, String serviceDescription, BigDecimal price) {
		super();
		this.id = id;
		this.serviceDescription = serviceDescription;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
    
    
}
