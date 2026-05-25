package com.exponab.invoice.service;

import com.exponab.invoice.dto.request.ProposalRequestDto;
import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.dto.response.ProposalClauseResponseDto;
import com.exponab.invoice.entity.ProposalClause;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.ProposalClauseRepository;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProposalService {

    @Autowired private CompanyRepository companyRepository;
    @Autowired private DocumentGenerationService documentService;
    @Autowired private JavaMailSender mailSender;
    @Autowired private ProposalClauseRepository proposalClauseRepository;

    @Transactional
    public void saveProposalDraft(ProposalRequestDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (dto.getAdditionalClauses() != null) {
            List<ProposalClause> clauses = dto.getAdditionalClauses().stream()
                    .map(content -> {
                        ProposalClause clause = new ProposalClause();
                        clause.setClauseContent(content);
                        clause.setCompany(company);
                        return clause;
                    }).collect(Collectors.toList());

            proposalClauseRepository.saveAll(clauses);
        }
    }

    @Transactional
    public void finalizeAndSend(Long companyId, DocumentGenerationRequest request) {
        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));

            // Adjust this validation based on where "Proposal" fits in your Enum lifecycle
//            if (company.getStatus() != CompanyStatus.REGISTERED && company.getStatus() != CompanyStatus.QUOTATION_SENT) {
//                throw new IllegalStateException("Cannot send proposal. Current status is " + company.getStatus());
//            }

            if (request.getSelectedClauseIds() == null || request.getSelectedClauseIds().isEmpty()) {
                List<Long> savedClauseIds = proposalClauseRepository.findByCompanyId(companyId)
                        .stream()
                        .map(ProposalClause::getId)
                        .collect(Collectors.toList());
                request.setSelectedClauseIds(savedClauseIds);
            }

            // Ensure you duplicate your generateAgreementPdf method in DocumentGenerationService and name it generateProposalPdf
            byte[] pdfBytes = documentService.generateProposalPdf(request);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(company.getEmail());
            helper.setSubject(request.getEmailSubject());

            String finalEmailBody = "Dear " + company.getCompanyName() + ",\n\n" + request.getEmailBody();
            helper.setText(finalEmailBody);

            ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("Proposal_" + company.getCompanyName().replaceAll("\\s+", "_") + ".pdf", pdfAttachment);

            mailSender.send(message);

            // Requires PROPOSAL_SENT to be added to CompanyStatus Enum
         //   company.updateStatus(CompanyStatus.PROPOSAL_SENT);

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Failed to finalize and send proposal for company " + companyId);
            e.printStackTrace();
            throw new RuntimeException("Failed to send proposal email", e);
        }
    }

    public List<ProposalClauseResponseDto> getClausesByCompany(Long companyId) {
        return proposalClauseRepository.findByCompanyId(companyId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProposalClauseResponseDto updateClause(Long clauseId, String newContent) {
        ProposalClause clause = proposalClauseRepository.findById(clauseId)
                .orElseThrow(() -> new RuntimeException("Proposal Clause not found"));

        clause.setClauseContent(newContent);
        clause = proposalClauseRepository.save(clause);
        return mapToResponseDto(clause);
    }

    @Transactional
    public void deleteClause(Long clauseId) {
        if (!proposalClauseRepository.existsById(clauseId)) {
            throw new RuntimeException("Proposal Clause not found");
        }
        proposalClauseRepository.deleteById(clauseId);
    }

    @Transactional
    public void acceptProposal(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));
//
//        if (!company.getStatus().canTransitionTo(CompanyStatus.PROPOSAL_ACCEPTED)) {
//            throw new IllegalStateException("Cannot accept proposal. Current status is " + company.getStatus());
//        }

     //   company.setStatus(CompanyStatus.PROPOSAL_ACCEPTED);
        companyRepository.save(company);
    }

    @Transactional
    public void rejectProposal(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));

//        if (!company.getStatus().canTransitionTo(CompanyStatus.PROPOSAL_REJECTED)) {
//            throw new IllegalStateException("Cannot reject proposal. Current status is " + company.getStatus());
//        }

     //   company.setStatus(CompanyStatus.PROPOSAL_REJECTED);
        companyRepository.save(company);
    }

    private ProposalClauseResponseDto mapToResponseDto(ProposalClause clause) {
        return new ProposalClauseResponseDto(
                clause.getId(),
                clause.getClauseContent(),
                clause.getCompany() != null ? clause.getCompany().getId() : null
        );
    }
}