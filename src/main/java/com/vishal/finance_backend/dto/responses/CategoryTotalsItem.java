package com.vishal.finance_backend.dto.responses;

public class CategoryTotalsItem {

    private String category;
    private Double incomeTotal;
    private Double expenseTotal;
    private Double netTotal;

    public CategoryTotalsItem() {
    }

    public CategoryTotalsItem(String category, Double incomeTotal, Double expenseTotal, Double netTotal) {
        this.category = category;
        this.incomeTotal = incomeTotal;
        this.expenseTotal = expenseTotal;
        this.netTotal = netTotal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }
}

