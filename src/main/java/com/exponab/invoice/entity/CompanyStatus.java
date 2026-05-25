package com.exponab.invoice.entity;

public enum CompanyStatus {
	ACTIVE,
	INACTIVE, REGISTERED

//    public boolean canTransitionTo(CompanyStatus nextStatus) {
//        return switch (this) {
//            // Path branches right at the start: either straight to Quotation OR to Proposal
//            case REGISTERED -> nextStatus == QUOTATION_SENT || nextStatus == PROPOSAL_SENT;
//
//            // From Proposal Sent, the client can only Accept or Reject
//            case PROPOSAL_SENT -> nextStatus == PROPOSAL_ACCEPTED || nextStatus == PROPOSAL_REJECTED;
//
//            // If accepted, the only way forward is a Quotation
//            case PROPOSAL_ACCEPTED -> nextStatus == QUOTATION_SENT;
//
//            // If rejected, the workflow dies here. No further actions allowed.
//            case PROPOSAL_REJECTED -> false;
//
//            // The rest of the linear workflow
//            case QUOTATION_SENT -> nextStatus == AGREEMENT_SENT;
//            case AGREEMENT_SENT -> nextStatus == AGREEMENT_SIGNED;
//            case AGREEMENT_SIGNED -> nextStatus == INVOICED;
//            case INVOICED -> false; 
//        };
//    }
}