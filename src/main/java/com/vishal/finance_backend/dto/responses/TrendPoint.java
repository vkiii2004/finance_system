package com.vishal.finance_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrendPoint {

    private String period;
    private Double incomeTotal;
    private Double expenseTotal;
    private Double netBalance;
}
