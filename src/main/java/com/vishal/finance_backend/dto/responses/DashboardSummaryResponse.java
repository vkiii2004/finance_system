package com.vishal.finance_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private Double totalIncome;
    private Double totalExpenses;
    private Double netBalance;
    private Long recordCount;
}
