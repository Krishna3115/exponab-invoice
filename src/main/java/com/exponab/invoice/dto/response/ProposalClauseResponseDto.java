package com.exponab.invoice.dto.response;



public class ProposalClauseResponseDto {
    private Long id;
    private String clauseContent;
    private Long companyId;
    
    public ProposalClauseResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public ProposalClauseResponseDto(Long id, String clauseContent, Long companyId) {
		super();
		this.id = id;
		this.clauseContent = clauseContent;
		this.companyId = companyId;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    
    
}