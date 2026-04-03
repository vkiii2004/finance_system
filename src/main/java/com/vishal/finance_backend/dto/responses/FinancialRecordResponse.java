package com.vishal.finance_backend.dto.responses;

import com.vishal.finance_backend.enums.RecordType;

import java.time.LocalDate;

public class FinancialRecordResponse {

    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String notes;

    public FinancialRecordResponse() {
    }

    public FinancialRecordResponse(Long id, Double amount, RecordType type, String category, LocalDate date, String notes) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

