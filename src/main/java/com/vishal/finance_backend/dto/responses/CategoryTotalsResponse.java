package com.vishal.finance_backend.dto.responses;

import java.util.List;

public class CategoryTotalsResponse {
    private List<CategoryTotalsItem> items;

    public CategoryTotalsResponse() {
    }

    public CategoryTotalsResponse(List<CategoryTotalsItem> items) {
        this.items = items;
    }

    public List<CategoryTotalsItem> getItems() {
        return items;
    }

    public void setItems(List<CategoryTotalsItem> items) {
        this.items = items;
    }
}

