package com.vishal.finance_backend.service;

import com.vishal.finance_backend.dto.responses.CategoryTotalsResponse;
import com.vishal.finance_backend.dto.responses.DashboardSummaryResponse;
import com.vishal.finance_backend.dto.responses.RecentActivityResponse;
import com.vishal.finance_backend.dto.responses.TrendsResponse;
import com.vishal.finance_backend.enums.TrendGranularity;

import java.time.LocalDate;

public interface DashboardService {

    DashboardSummaryResponse getSummary();

    CategoryTotalsResponse getCategoryTotals();

    RecentActivityResponse getRecentActivity(int limit);

    TrendsResponse getTrends(TrendGranularity granularity, LocalDate startDate, LocalDate endDate);
}

