package com.vishal.finance_backend.dto.responses;

import com.vishal.finance_backend.enums.TrendGranularity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrendsResponse {

    private TrendGranularity granularity;
    private List<TrendPoint> points;
}
