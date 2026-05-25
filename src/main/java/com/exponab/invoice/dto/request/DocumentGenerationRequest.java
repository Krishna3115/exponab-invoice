package com.exponab.invoice.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

public class DocumentGenerationRequest {
    
//    @NotBlank(message = "template content is required")
    private String templateContent;
    private List<Long> selectedClauseIds;

    @NotBlank(message = "Email subject is required")
    private String emailSubject;

    @NotBlank(message = "Email body is required")
    private String emailBody;
    
    public DocumentGenerationRequest() {
		// TODO Auto-generated constructor stub
	}
    
    

    public DocumentGenerationRequest(String templateContent, List<Long> selectedClauseIds,
			@NotBlank(message = "Email subject is required") String emailSubject,
			@NotBlank(message = "Email body is required") String emailBody) {
		super();
		this.templateContent = templateContent;
		this.selectedClauseIds = selectedClauseIds;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
	}



	public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public List<Long> getSelectedClauseIds() {
        return selectedClauseIds;
    }

    public void setSelectedClauseIds(List<Long> selectedClauseIds) {
        this.selectedClauseIds = selectedClauseIds;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}