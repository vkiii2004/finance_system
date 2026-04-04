package com.vishal.finance_backend.controller;

import com.vishal.finance_backend.dto.responses.CategoryTotalsResponse;
import com.vishal.finance_backend.dto.responses.DashboardSummaryResponse;
import com.vishal.finance_backend.dto.responses.RecentActivityResponse;
import com.vishal.finance_backend.dto.responses.TrendsResponse;
import com.vishal.finance_backend.enums.TrendGranularity;
import com.vishal.finance_backend.exception.ApiException;
import com.vishal.finance_backend.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    @GetMapping("/summary")
    public DashboardSummaryResponse summary() {
        return dashboardService.getSummary();
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    @GetMapping("/category-totals")
    public CategoryTotalsResponse categoryTotals() {
        return dashboardService.getCategoryTotals();
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    @GetMapping("/recent-activity")
    public RecentActivityResponse recentActivity(
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit
    ) {
        return dashboardService.getRecentActivity(limit);
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    @GetMapping("/trends")
    public TrendsResponse trends(
            @RequestParam(defaultValue = "MONTHLY") String granularity,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        TrendGranularity g;
        try {
            g = TrendGranularity.valueOf(granularity.trim().toUpperCase());
        } catch (Exception e) {
            throw ApiException.badRequest("Invalid granularity. Use MONTHLY or WEEKLY.");
        }
        return dashboardService.getTrends(g, startDate, endDate);
    }
}

