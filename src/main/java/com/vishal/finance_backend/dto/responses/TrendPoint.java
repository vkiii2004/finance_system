package com.vishal.finance_backend.dto.responses;

public class TrendPoint {

    private String period;
    private Double incomeTotal;
    private Double expenseTotal;
    private Double netBalance;

    public TrendPoint() {
    }

    public TrendPoint(String period, Double incomeTotal, Double expenseTotal, Double netBalance) {
        this.period = period;
        this.incomeTotal = incomeTotal;
        this.expenseTotal = expenseTotal;
        this.netBalance = netBalance;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(Double incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public Double getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(Double expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public Double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(Double netBalance) {
        this.netBalance = netBalance;
    }
}

