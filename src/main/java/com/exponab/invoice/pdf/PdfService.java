package com.exponab.invoice.pdf;

import org.springframework.stereotype.Service;
import com.exponab.invoice.entity.SalesReport;
import com.exponab.invoice.repo.SalesReportRepository;

@Service
public class PdfService {

    private final SalesReportRepository salesReportRepository;

    public PdfService(SalesReportRepository salesReportRepository) {
        this.salesReportRepository = salesReportRepository;
    }

    public byte[] generateSalesReportPdf(Long reportId) {
        SalesReport report = salesReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Sales Report not found"));

        return PdfLayoutBuilder.buildSalesReport(report);
    }
}