package com.vishal.finance_backend.dto.responses;

import java.util.List;

public class RecentActivityResponse {

    private List<RecentActivityItem> items;

    public RecentActivityResponse() {
    }

    public RecentActivityResponse(List<RecentActivityItem> items) {
        this.items = items;
    }

    public List<RecentActivityItem> getItems() {
        return items;
    }

    public void setItems(List<RecentActivityItem> items) {
        this.items = items;
    }
}

