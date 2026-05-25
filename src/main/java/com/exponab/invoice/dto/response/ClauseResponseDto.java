package com.exponab.invoice.dto.response;




public class ClauseResponseDto {
    private Long id;
    private String clauseContent;
    private Long companyId; // Just the ID, not the whole company object!
    
    public ClauseResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public ClauseResponseDto(Long id, String clauseContent, Long companyId) {
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