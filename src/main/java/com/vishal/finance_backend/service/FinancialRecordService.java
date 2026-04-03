package com.vishal.finance_backend.service;

import com.vishal.finance_backend.dto.requests.CreateRecordRequest;
import com.vishal.finance_backend.dto.requests.UpdateRecordRequest;
import com.vishal.finance_backend.dto.responses.FinancialRecordResponse;
import com.vishal.finance_backend.enums.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FinancialRecordService {

    FinancialRecordResponse create(CreateRecordRequest request);

    FinancialRecordResponse getById(Long id);

    Page<FinancialRecordResponse> search(
            RecordType type,
            String category,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    FinancialRecordResponse update(Long id, UpdateRecordRequest request);

    void delete(Long id);
}

