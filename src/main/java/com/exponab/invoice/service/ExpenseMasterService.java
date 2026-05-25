package com.exponab.invoice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exponab.invoice.dto.request.ExpenseMasterRequestDto;
import com.exponab.invoice.dto.response.ExpenseMasterResponseDto;
import com.exponab.invoice.entity.ExpenseMaster;
import com.exponab.invoice.repo.ExpenseMasterRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExpenseMasterService {

	private final ExpenseMasterRepository repository;

    public ExpenseMasterService(
            ExpenseMasterRepository repository) {

        this.repository = repository;
    }

    // CREATE EXPENSE
    public ExpenseMasterResponseDto create(
            ExpenseMasterRequestDto request) {

        ExpenseMaster expense = new ExpenseMaster();

        expense.setExpenseName(
                request.getExpenseName());

        expense.setDefaultQuantity(
                request.getDefaultQuantity());

        expense.setDefaultRate(
                request.getDefaultRate());

        expense.setActive(true);

        ExpenseMaster saved =
                repository.save(expense);

        return mapToResponse(saved);
    }

    // GET ALL
    public List<ExpenseMasterResponseDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public ExpenseMasterResponseDto getById(Long id) {

        ExpenseMaster expense =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expense not found"));

        return mapToResponse(expense);
    }

    // UPDATE
    public ExpenseMasterResponseDto update(
            Long id,
            ExpenseMasterRequestDto request) {

        ExpenseMaster expense =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expense not found"));

        expense.setExpenseName(
                request.getExpenseName());

        expense.setDefaultQuantity(
                request.getDefaultQuantity());

        expense.setDefaultRate(
                request.getDefaultRate());

        ExpenseMaster updated =
                repository.save(expense);

        return mapToResponse(updated);
    }

    // SOFT DELETE / DEACTIVATE
    public void deactivate(Long id) {

        ExpenseMaster expense =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expense not found"));

        expense.setActive(false);

        repository.save(expense);
    }

    // DTO MAPPING
    private ExpenseMasterResponseDto mapToResponse(
            ExpenseMaster expense) {

        return new ExpenseMasterResponseDto(
                expense.getId(),
                expense.getExpenseName(),
                expense.getDefaultQuantity(),
                expense.getDefaultRate(),
                expense.getActive()
        );
    }
}
