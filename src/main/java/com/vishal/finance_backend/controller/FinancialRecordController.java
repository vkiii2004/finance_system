package com.vishal.finance_backend.controller;

import com.vishal.finance_backend.dto.requests.CreateRecordRequest;
import com.vishal.finance_backend.dto.requests.UpdateRecordRequest;
import com.vishal.finance_backend.dto.responses.FinancialRecordResponse;
import com.vishal.finance_backend.enums.RecordType;
import com.vishal.finance_backend.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    public FinancialRecordController(FinancialRecordService financialRecordService) {
        this.financialRecordService = financialRecordService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FinancialRecordResponse create(@Valid @RequestBody CreateRecordRequest request) {
        return financialRecordService.create(request);
    }

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    @GetMapping
    public Page<FinancialRecordResponse> search(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        return financialRecordService.search(type, category, startDate, endDate, pageable);
    }

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    @GetMapping("/{id}")
    public FinancialRecordResponse getById(@PathVariable Long id) {
        return financialRecordService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public FinancialRecordResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecordRequest request
    ) {
        return financialRecordService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        financialRecordService.delete(id);
    }
}

