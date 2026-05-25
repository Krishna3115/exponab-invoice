package com.exponab.invoice.service;

import com.exponab.invoice.dto.request.AgreementRequestDto;
import com.exponab.invoice.dto.request.DocumentGenerationRequest;
import com.exponab.invoice.dto.response.ClauseResponseDto;
import com.exponab.invoice.entity.Clause;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.ClauseRepository;

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
public class AgreementService {

    @Autowired private CompanyRepository companyRepository;
    @Autowired private DocumentGenerationService documentService;
    @Autowired private JavaMailSender mailSender;
    @Autowired private ClauseRepository clauseRepository;

    @Transactional
    public void saveAgreementDraft(AgreementRequestDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (dto.getAdditionalClauses() != null) {
            List<Clause> clauses = dto.getAdditionalClauses().stream()
                    .map(content -> {
                        Clause clause = new Clause();
                        clause.setClauseContent(content);
                        clause.setCompany(company);
                        return clause;
                    }).collect(Collectors.toList());

            clauseRepository.saveAll(clauses);
        }
    }

    @Transactional
    public void finalizeAndSend(Long companyId, DocumentGenerationRequest request) {
        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));

          //  if (company.getStatus() != CompanyStatus.QUOTATION_SENT) {
        //        throw new IllegalStateException("Cannot send agreement. Current status is " + company.getStatus() + ". Expected: QUOTATION_SENT");
         //   }

            // FIX: If the frontend sent no selectedClauseIds (or an empty list),
            // fall back to all clauses saved for this company in the DB.
            // This handles the case where the draft was saved just before finalize
            // and selectedClauseIds was left empty by the frontend.
            if (request.getSelectedClauseIds() == null || request.getSelectedClauseIds().isEmpty()) {
                List<Long> savedClauseIds = clauseRepository.findByCompanyId(companyId)
                        .stream()
                        .map(Clause::getId)
                        .collect(Collectors.toList());
                request.setSelectedClauseIds(savedClauseIds);
            }

            byte[] pdfBytes = documentService.generateAgreementPdf(request);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(company.getEmail());
            helper.setSubject(request.getEmailSubject());

            String finalEmailBody = "Dear " + company.getCompanyName() + ",\n\n" + request.getEmailBody();
            helper.setText(finalEmailBody);

            ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("Agreement_" + company.getCompanyName().replaceAll("\\s+", "_") + ".pdf", pdfAttachment);

            mailSender.send(message);

            //company.updateStatus(CompanyStatus.AGREEMENT_SENT);

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Failed to finalize and send agreement for company " + companyId);
            e.printStackTrace();
            throw new RuntimeException("Failed to send agreement email", e);
        }
    }

    public List<ClauseResponseDto> getClausesByCompany(Long companyId) {
        return clauseRepository.findByCompanyId(companyId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClauseResponseDto updateClause(Long clauseId, String newContent) {
        Clause clause = clauseRepository.findById(clauseId)
                .orElseThrow(() -> new RuntimeException("Clause not found"));

        clause.setClauseContent(newContent);
        clause = clauseRepository.save(clause);
        return mapToResponseDto(clause);
    }

    @Transactional
    public void deleteClause(Long clauseId) {
        if (!clauseRepository.existsById(clauseId)) {
            throw new RuntimeException("Clause not found");
        }
        clauseRepository.deleteById(clauseId);
    }

    // --- HELPER MAPPER METHOD ---
    private ClauseResponseDto mapToResponseDto(Clause clause) {
        return new ClauseResponseDto(
                clause.getId(),
                clause.getClauseContent(),
                clause.getCompany() != null ? clause.getCompany().getId() : null
        );
    }
}