package com.exponab.invoice.entity;

import jakarta.persistence.*;


@Entity
public class ProposalClause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String clauseContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    
    public ProposalClause() {
		// TODO Auto-generated constructor stub
	}

	public ProposalClause(Long id, String clauseContent, Company company) {
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