package com.exponab.invoice.dto.request;

import jakarta.validation.constraints.NotBlank;

public class QuotationSendRequestDto {

    @NotBlank(message = "Email subject is required")
    private String emailSubject;

    @NotBlank(message = "Email body is required")
    private String emailBody;
    
    public QuotationSendRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public QuotationSendRequestDto(@NotBlank(message = "Email subject is required") String emailSubject,
			@NotBlank(message = "Email body is required") String emailBody) {
		super();
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
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