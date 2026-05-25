package com.exponab.invoice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "material_receiving_items")
public class MaterialReceivingItem {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String description;
	    private Integer quantity;
	    private Double rate;
	    private Double total;
	    
	    public MaterialReceivingItem() {
			// TODO Auto-generated constructor stub
		}

		public MaterialReceivingItem(Long id, String description, Integer quantity, Double rate, Double total) {
			super();
			this.id = id;
			this.description = description;
			this.quantity = quantity;
			this.rate = rate;
			this.total = total;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Double getRate() {
			return rate;
		}

		public void setRate(Double rate) {
			this.rate = rate;
		}
	    
		public Double getTotal() {
			return total;
		}

		public void setTotal(Double total) {
			this.total = total;
		}
	
}
