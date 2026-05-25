package com.exponab.invoice.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;

    @Column(unique = true)
    private String gstNumber;

    private String contactPersonName;
    private String contactPersonPhone;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clause> customClauses = new ArrayList<>();

  @Enumerated(EnumType.STRING)
//    @Column(nullable = false, length = 50)
    private CompanyStatus status = CompanyStatus.REGISTERED;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
	public Company() {
		// TODO Auto-generated constructor stub
	}   
	
	

    public Company(Long id, String companyName, String email, String phone, String address, String city, String state,
			String country, String pincode, String gstNumber, String contactPersonName, String contactPersonPhone,
			List<Clause> customClauses, CompanyStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.gstNumber = gstNumber;
		this.contactPersonName = contactPersonName;
		this.contactPersonPhone = contactPersonPhone;
		this.customClauses = customClauses;
	//	this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPincode() {
		return pincode;
	}



	public void setPincode(String pincode) {
		this.pincode = pincode;
	}



	public String getGstNumber() {
		return gstNumber;
	}



	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}



	public String getContactPersonName() {
		return contactPersonName;
	}



	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}



	public String getContactPersonPhone() {
		return contactPersonPhone;
	}



	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}



	public List<Clause> getCustomClauses() {
		return customClauses;
	}



	public void setCustomClauses(List<Clause> customClauses) {
		this.customClauses = customClauses;
	}



	public CompanyStatus getStatus() {
		return status;
	}



	public void setStatus(CompanyStatus status) {
		this.status = status;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    




//	public void updateStatus(CompanyStatus newStatus) {
//        if (this.status == null) {
//            this.status = newStatus;
//        } else if (this.status.canTransitionTo(newStatus)) {
//            this.status = newStatus;
//        } else {
//            throw new IllegalStateException("Invalid status transition from " + 
//                                            this.status + " to " + newStatus);
//        }
    
}