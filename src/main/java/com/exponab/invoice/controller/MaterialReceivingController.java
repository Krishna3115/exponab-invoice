package com.exponab.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exponab.invoice.dto.request.MaterialReceivingRequestDto;
import com.exponab.invoice.service.MaterialReceivingService;

@RestController
@RequestMapping("/api/material-receiving")
@CrossOrigin
public class MaterialReceivingController {

	   @Autowired
	    private MaterialReceivingService service;

	    // CREATE GRN
	    @PostMapping
	    public ResponseEntity<?> create(@RequestBody MaterialReceivingRequestDto dto) {
	        return ResponseEntity.ok(service.save(dto));
	    }

	    // GET ALL GRN
	    @GetMapping
	    public ResponseEntity<?> getAll() {
	        return ResponseEntity.ok(service.getAll());
	    }

	    // GET BY ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getById(@PathVariable Long id) {
	        return ResponseEntity.ok(service.getById(id));
	    }
}
