package com.exponab.invoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.MaterialReceivingRequestDto;
import com.exponab.invoice.entity.MaterialReceiving;
import com.exponab.invoice.entity.MaterialReceivingItem;
import com.exponab.invoice.repo.MaterialReceivingRepository;

@Service
public class MaterialReceivingService {

    @Autowired
    private MaterialReceivingRepository repo;

    public MaterialReceiving save(MaterialReceivingRequestDto dto) {

        MaterialReceiving grn = new MaterialReceiving();

        // HEADER DATA
        grn.setCompanyId(dto.getCompanyId());
        grn.setPurchaseOrderId(dto.getPurchaseOrderId());
        grn.setReceivedDate(dto.getReceivedDate());
        grn.setStatus(dto.getStatus());
        grn.setInvoiceNumber(dto.getInvoiceNumber());

        // ITEMS (SAFE NULL CHECK)
        List<MaterialReceivingItem> items = null;

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {

            items = dto.getItems().stream().map(i -> {

                MaterialReceivingItem item = new MaterialReceivingItem();

                item.setDescription(i.getDescription());
                item.setQuantity(i.getQuantity());
                item.setRate(i.getRate());

                // ✅ CALCULATE TOTAL
                double total = 0;

                if (i.getQuantity() != null && i.getRate() != null) {
                    total = i.getQuantity() * i.getRate();
                }

                item.setTotal(total);

                return item;
            }).toList();
        }

        grn.setItems(items);

        return repo.save(grn);
    }

    // OPTIONAL: GET ALL GRN
    public List<MaterialReceiving> getAll() {
        return repo.findAll();
    }

    // OPTIONAL: GET BY ID
    public MaterialReceiving getById(Long id) {
        return repo.findById(id).orElse(null);
    }
}