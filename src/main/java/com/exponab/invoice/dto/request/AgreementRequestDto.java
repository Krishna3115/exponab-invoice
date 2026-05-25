package com.exponab.invoice.dto.request;


import java.util.List;


public class AgreementRequestDto {
    private Long companyId;
    private Long templateId; // The ID of the selected template
    private List<String> additionalClauses; // Just a list of strings from the UI
    
    public AgreementRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public AgreementRequestDto(Long companyId, Long templateId, List<String> additionalClauses) {
		super();
		this.companyId = companyId;
		this.templateId = templateId;
		this.additionalClauses = additionalClauses;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public List<String> getAdditionalClauses() {
		return additionalClauses;
	}

	public void setAdditionalClauses(List<String> additionalClauses) {
		this.additionalClauses = additionalClauses;
	}
    
    
}