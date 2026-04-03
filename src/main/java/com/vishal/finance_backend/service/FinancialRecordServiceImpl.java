package com.vishal.finance_backend.service;

import com.vishal.finance_backend.Entity.FinancialRecords;
import com.vishal.finance_backend.Entity.User;
import com.vishal.finance_backend.dto.requests.CreateRecordRequest;
import com.vishal.finance_backend.dto.requests.UpdateRecordRequest;
import com.vishal.finance_backend.dto.responses.FinancialRecordResponse;
import com.vishal.finance_backend.enums.RecordType;
import com.vishal.finance_backend.exception.ResourceNotFoundException;
import com.vishal.finance_backend.repository.FinancialRecordsRepository;
import com.vishal.finance_backend.repository.UserRepository;
import com.vishal.finance_backend.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordsRepository financialRecordsRepository;
    private final UserRepository userRepository;

    public FinancialRecordServiceImpl(FinancialRecordsRepository financialRecordsRepository,
                                       UserRepository userRepository) {
        this.financialRecordsRepository = financialRecordsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FinancialRecordResponse create(CreateRecordRequest request) {
        User user = currentUser();

        FinancialRecords record = new FinancialRecords();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        record.setUser(user);

        FinancialRecords saved = financialRecordsRepository.save(record);
        return toResponse(saved);
    }

    @Override
    public FinancialRecordResponse getById(Long id) {
        User user = currentUser();

        FinancialRecords record = financialRecordsRepository
                .findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial record not found"));

        return toResponse(record);
    }

    @Override
    public Page<FinancialRecordResponse> search(RecordType type,
                                                 String category,
                                                 LocalDate startDate,
                                                 LocalDate endDate,
                                                 Pageable pageable) {
        User user = currentUser();
        return financialRecordsRepository
                .search(user.getId(), type, category, startDate, endDate, pageable)
                .map(this::toResponse);
    }

    @Override
    public FinancialRecordResponse update(Long id, UpdateRecordRequest request) {
        User user = currentUser();

        FinancialRecords record = financialRecordsRepository
                .findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial record not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());

        return toResponse(financialRecordsRepository.save(record));
    }

    @Override
    public void delete(Long id) {
        User user = currentUser();

        FinancialRecords record = financialRecordsRepository
                .findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial record not found"));

        financialRecordsRepository.delete(Objects.requireNonNull(record, "record"));
    }

    private User currentUser() {
        String username = SecurityUtils.getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    private FinancialRecordResponse toResponse(FinancialRecords record) {
        return new FinancialRecordResponse(
                record.getId(),
                record.getAmount(),
                record.getType(),
                record.getCategory(),
                record.getDate(),
                record.getNotes()
        );
    }
}

