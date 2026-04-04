package com.vishal.finance_backend.service;

import com.vishal.finance_backend.Entity.FinancialRecords;
import com.vishal.finance_backend.Entity.User;
import com.vishal.finance_backend.dto.responses.CategoryTotalsItem;
import com.vishal.finance_backend.dto.responses.CategoryTotalsResponse;
import com.vishal.finance_backend.dto.responses.DashboardSummaryResponse;
import com.vishal.finance_backend.dto.responses.RecentActivityItem;
import com.vishal.finance_backend.dto.responses.RecentActivityResponse;
import com.vishal.finance_backend.dto.responses.TrendPoint;
import com.vishal.finance_backend.dto.responses.TrendsResponse;
import com.vishal.finance_backend.enums.RecordType;
import com.vishal.finance_backend.enums.TrendGranularity;
import com.vishal.finance_backend.exception.ApiException;
import com.vishal.finance_backend.repository.FinancialRecordsRepository;
import com.vishal.finance_backend.repository.UserRepository;
import com.vishal.finance_backend.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordsRepository financialRecordsRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(FinancialRecordsRepository financialRecordsRepository,
                                 UserRepository userRepository) {
        this.financialRecordsRepository = financialRecordsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardSummaryResponse getSummary() {
        User user = currentUser();
        List<FinancialRecords> records = financialRecordsRepository.findByUserId(user.getId());

        double income = 0.0;
        double expenses = 0.0;

        for (FinancialRecords r : records) {
            if (r.getType() == RecordType.INCOME) {
                income += safe(r.getAmount());
            } else if (r.getType() == RecordType.EXPENSE) {
                expenses += safe(r.getAmount());
            }
        }

        double net = income - expenses;
        return new DashboardSummaryResponse(income, expenses, net, (long) records.size());
    }

    @Override
    public CategoryTotalsResponse getCategoryTotals() {
        User user = currentUser();
        List<FinancialRecords> records = financialRecordsRepository.findByUserId(user.getId());

        class Totals {
            double income = 0.0;
            double expense = 0.0;
        }

        Map<String, Totals> totalsByCategory = new HashMap<>();
        for (FinancialRecords r : records) {
            String category = r.getCategory();
            totalsByCategory.putIfAbsent(category, new Totals());
            Totals t = totalsByCategory.get(category);
            if (r.getType() == RecordType.INCOME) {
                t.income += safe(r.getAmount());
            } else if (r.getType() == RecordType.EXPENSE) {
                t.expense += safe(r.getAmount());
            }
        }

        List<CategoryTotalsItem> items = new ArrayList<>();
        for (Map.Entry<String, Totals> e : totalsByCategory.entrySet()) {
            String category = e.getKey();
            Totals t = e.getValue();
            double net = t.income - t.expense;
            items.add(new CategoryTotalsItem(category, t.income, t.expense, net));
        }

        items.sort(Comparator.comparing(CategoryTotalsItem::getNetTotal).reversed());
        return new CategoryTotalsResponse(items);
    }

    @Override
    public RecentActivityResponse getRecentActivity(int limit) {
        User user = currentUser();

        int size = Math.max(1, Math.min(100, limit));
        Pageable pageable = PageRequest.of(0, size,
                Sort.by(Sort.Order.desc("date"), Sort.Order.desc("id")));

        Page<FinancialRecords> page = financialRecordsRepository.findByUserId(user.getId(), pageable);
        List<RecentActivityItem> items = new ArrayList<>();

        for (FinancialRecords r : page.getContent()) {
            items.add(new RecentActivityItem(
                    r.getId(),
                    r.getAmount(),
                    r.getType(),
                    r.getCategory(),
                    r.getDate(),
                    r.getNotes()
            ));
        }
        return new RecentActivityResponse(items);
    }

    @Override
    public TrendsResponse getTrends(TrendGranularity granularity, LocalDate startDate, LocalDate endDate) {
        User user = currentUser();
        LocalDate today = LocalDate.now();

        if (granularity == null) {
            granularity = TrendGranularity.MONTHLY;
        }

        LocalDate resolvedStart;
        LocalDate resolvedEnd;

        if (startDate == null) {
            if (granularity == TrendGranularity.MONTHLY) {
                resolvedStart = today.minusMonths(11).withDayOfMonth(1);
            } else {
                resolvedStart = today.minusWeeks(7);
            }
        } else {
            resolvedStart = startDate;
        }

        resolvedEnd = (endDate == null) ? today : endDate;

        if (resolvedEnd.isBefore(resolvedStart)) {
            resolvedEnd = resolvedStart;
        }

        List<FinancialRecords> records = financialRecordsRepository
                .findByUserIdAndDateBetween(user.getId(), resolvedStart, resolvedEnd);

        if (granularity == TrendGranularity.MONTHLY) {
            return new TrendsResponse(granularity, monthlySeries(records, resolvedStart, resolvedEnd));
        }
        return new TrendsResponse(granularity, weeklySeries(records, resolvedStart, resolvedEnd));
    }

    private List<TrendPoint> monthlySeries(List<FinancialRecords> records, LocalDate start, LocalDate end) {
        YearMonth startYm = YearMonth.from(start);
        YearMonth endYm = YearMonth.from(end);

        class Totals {
            double income = 0.0;
            double expense = 0.0;
        }

        Map<YearMonth, Totals> totals = new HashMap<>();
        for (FinancialRecords r : records) {
            YearMonth ym = YearMonth.from(r.getDate());
            totals.putIfAbsent(ym, new Totals());
            Totals t = totals.get(ym);
            if (r.getType() == RecordType.INCOME) {
                t.income += safe(r.getAmount());
            } else if (r.getType() == RecordType.EXPENSE) {
                t.expense += safe(r.getAmount());
            }
        }

        List<TrendPoint> points = new ArrayList<>();
        YearMonth cursor = startYm;
        while (!cursor.isAfter(endYm)) {
            Totals t = totals.getOrDefault(cursor, new Totals());
            double net = t.income - t.expense;
            points.add(new TrendPoint(
                    cursor.toString(),
                    t.income,
                    t.expense,
                    net
            ));
            cursor = cursor.plusMonths(1);
        }
        return points;
    }

    private List<TrendPoint> weeklySeries(List<FinancialRecords> records, LocalDate start, LocalDate end) {

        LocalDate startWeek = weekStart(start);
        LocalDate endWeek = weekStart(end);

        class Totals {
            double income = 0.0;
            double expense = 0.0;
        }

        Map<LocalDate, Totals> totals = new HashMap<>();
        for (FinancialRecords r : records) {
            LocalDate week = weekStart(r.getDate());
            totals.putIfAbsent(week, new Totals());
            Totals t = totals.get(week);
            if (r.getType() == RecordType.INCOME) {
                t.income += safe(r.getAmount());
            } else if (r.getType() == RecordType.EXPENSE) {
                t.expense += safe(r.getAmount());
            }
        }

        List<TrendPoint> points = new ArrayList<>();
        LocalDate cursor = startWeek;
        while (!cursor.isAfter(endWeek)) {
            Totals t = totals.getOrDefault(cursor, new Totals());
            double net = t.income - t.expense;
            points.add(new TrendPoint(
                    cursor.toString(),
                    t.income,
                    t.expense,
                    net
            ));
            cursor = cursor.plusWeeks(1);
        }
        return points;
    }

    private LocalDate weekStart(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private double safe(Double value) {
        return value == null ? 0.0 : value;
    }

    private User currentUser() {
        String username = SecurityUtils.getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> ApiException.notFound("Authenticated user not found"));
    }
}

