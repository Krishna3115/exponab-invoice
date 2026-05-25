package com.exponab.invoice.dto.request;

import java.util.List;


public class ProposalRequestDto {
    private Long companyId;
    private List<String> additionalClauses;
    
    public ProposalRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public ProposalRequestDto(Long companyId, List<String> additionalClauses) {
		super();
		this.companyId = companyId;
		this.additionalClauses = additionalClauses;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<String> getAdditionalClauses() {
		return additionalClauses;
	}

	public void setAdditionalClauses(List<String> additionalClauses) {
		this.additionalClauses = additionalClauses;
	}
    
    
}