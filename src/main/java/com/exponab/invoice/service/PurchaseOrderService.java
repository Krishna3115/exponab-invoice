package com.exponab.invoice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.PurchaseOrderItemDto;
import com.exponab.invoice.dto.request.PurchaseOrderRequestDto;
import com.exponab.invoice.dto.response.PurchaseOrderResponseDto;
import com.exponab.invoice.entity.Company;
import com.exponab.invoice.entity.PurchaseOrder;
import com.exponab.invoice.entity.PurchaseOrderItem;
import com.exponab.invoice.entity.PurchaseOrderStatus;
import com.exponab.invoice.repo.CompanyRepository;
import com.exponab.invoice.repo.PurchaseOrderRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class PurchaseOrderService {

	 private final PurchaseOrderRepo repository;
	    private final CompanyRepository clientRepository;

	    public PurchaseOrderService(PurchaseOrderRepo repository,
				                CompanyRepository clientRepository) {
				this.repository = repository;
				this.clientRepository = clientRepository;
				}
	    
	    
	    public PurchaseOrderResponseDto createPO(PurchaseOrderRequestDto request) {

	        Company client = clientRepository.findById(request.getExporterId())
	                .orElseThrow(() -> new RuntimeException("Client not found"));

	        PurchaseOrder po = new PurchaseOrder();

	        po.setPoNumber(generatePoNumber());
	        po.setPoDate(LocalDate.now());
	        po.setExporter(client);

	        po.setCountryOfOrigin(request.getCountryOfOrigin());
	        po.setDestinationPort(request.getDestinationPort());
	        po.setIncoterms(request.getIncoterms());
	        po.setTransportMode(request.getTransportMode());

	        po.setQualityStandard(request.getQualityStandard());
	        po.setPaymentTerms(request.getPaymentTerms());
	        po.setDeliveryTerms(request.getDeliveryTerms());

	        po.setStatus(PurchaseOrderStatus.CREATED);

	        List<PurchaseOrderItem> items = new ArrayList<>();
	        BigDecimal total = BigDecimal.ZERO;

	        for (PurchaseOrderItemDto dto : request.getItems()) {

	            BigDecimal itemTotal =
	                    dto.getUnitPrice().multiply(dto.getQuantity());

	            PurchaseOrderItem item = new PurchaseOrderItem();
	            item.setPurchaseOrder(po);
	            item.setCommodity(dto.getCommodity());
	            item.setQuantity(dto.getQuantity());
	            item.setUnit(dto.getUnit());
	            item.setUnitPrice(dto.getUnitPrice());
	            item.setTotalPrice(itemTotal);

	            items.add(item);
	            total = total.add(itemTotal);
	        }

	        po.setItems(items);
	        po.setGrandTotal(total);

	        PurchaseOrder saved = repository.save(po);

	        return mapToResponse(saved);
	    }

	    private String generatePoNumber() {
	        return "PO-" + System.currentTimeMillis();
	    }

	    private PurchaseOrderResponseDto mapToResponse(PurchaseOrder po) {

	        return new PurchaseOrderResponseDto(
	                po.getId(),
	                po.getPoNumber(),
	                po.getPoDate(),
	                po.getExporter().getCompanyName(),
	                po.getGrandTotal(),
	                po.getStatus().name()
	        );
	    }
	   
	    public PurchaseOrderResponseDto getById(Long id) {
	        PurchaseOrder po = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("PO not found"));

	        return mapToResponse(po);
	    }

	    
	    public List<PurchaseOrderResponseDto> getAll() {
	        return repository.findAll()
	                .stream()
	                .map(this::mapToResponse)
	                .toList();
	    }
}

