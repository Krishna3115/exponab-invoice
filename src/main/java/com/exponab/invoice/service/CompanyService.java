package com.exponab.invoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.CompanyRequestDto;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.CompanyStatus;
import com.exponab.invoice.repo.CompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company registerCompany(CompanyRequestDto dto) {
        if (companyRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Company with this email already exists");
        }
        if (dto.getGstNumber() != null && companyRepository.existsByGstNumber(dto.getGstNumber())) {
            throw new RuntimeException("Company with this GST number already exists");
        }

        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        company.setAddress(dto.getAddress());
        company.setCity(dto.getCity());
        company.setState(dto.getState());
        company.setCountry(dto.getCountry());
        company.setPincode(dto.getPincode());
        company.setGstNumber(dto.getGstNumber());
        company.setContactPersonName(dto.getContactPersonName());
        company.setContactPersonPhone(dto.getContactPersonPhone());
      //  company.setStatus(CompanyStatus.ACTIVE);

        return companyRepository.save(company);
    }

//    @Transactional
//    public Company markAgreementSigned(Long companyId) {
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));
//
//        if (company.getStatus() != CompanyStatus.AGREEMENT_SENT) {
//            throw new IllegalStateException("Cannot mark agreement as signed. Current status is: " + company.getStatus()
//                    + ". Expected: AGREEMENT_SENT.");
//        }
//
//        company.updateStatus(CompanyStatus.AGREEMENT_SIGNED);
//        return companyRepository.save(company);
//    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public Company updateCompany(Long id, CompanyRequestDto dto) {
        Company company = getCompanyById(id);
        company.setCompanyName(dto.getCompanyName());
        company.setPhone(dto.getPhone());
        company.setAddress(dto.getAddress());
        company.setCity(dto.getCity());
        company.setState(dto.getState());
        company.setCountry(dto.getCountry());
        company.setPincode(dto.getPincode());
        company.setGstNumber(dto.getGstNumber());
        company.setContactPersonName(dto.getContactPersonName());
        company.setContactPersonPhone(dto.getContactPersonPhone());
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> getCompaniesByStatus(CompanyStatus status) {
        return companyRepository.findByStatus(status);
    }
}
