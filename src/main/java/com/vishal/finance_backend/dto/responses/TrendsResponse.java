package com.vishal.finance_backend.dto.responses;

import com.vishal.finance_backend.enums.TrendGranularity;

import java.util.List;

public class TrendsResponse {

    private TrendGranularity granularity;
    private List<TrendPoint> points;

    public TrendsResponse() {
    }

    public TrendsResponse(TrendGranularity granularity, List<TrendPoint> points) {
        this.granularity = granularity;
        this.points = points;
    }

    public TrendGranularity getGranularity() {
        return granularity;
    }

    public void setGranularity(TrendGranularity granularity) {
        this.granularity = granularity;
    }

    public List<TrendPoint> getPoints() {
        return points;
    }

    public void setPoints(List<TrendPoint> points) {
        this.points = points;
    }
}

