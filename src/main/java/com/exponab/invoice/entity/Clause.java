package com.exponab.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "clauses")
public class Clause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String clauseContent;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;
    
	public Clause() {
		// TODO Auto-generated constructor stub
	}

	public Clause(Long id, String clauseContent, Company company) {
		super();
		this.id = id;
		this.clauseContent = clauseContent;
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClauseContent() {
		return clauseContent;
	}

	public void setClauseContent(String clauseContent) {
		this.clauseContent = clauseContent;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
}