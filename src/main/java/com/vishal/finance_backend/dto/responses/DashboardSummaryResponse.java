package com.vishal.finance_backend.dto.responses;

public class DashboardSummaryResponse {

    private Double totalIncome;
    private Double totalExpenses;
    private Double netBalance;
    private Long recordCount;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(Double totalIncome, Double totalExpenses, Double netBalance, Long recordCount) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netBalance = netBalance;
        this.recordCount = recordCount;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(Double netBalance) {
        this.netBalance = netBalance;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }
}

