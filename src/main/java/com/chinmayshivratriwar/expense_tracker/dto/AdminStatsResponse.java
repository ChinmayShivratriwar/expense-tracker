package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminStatsResponse {
    private String totalUsers;
    private String totalTransactions;
    private String totalBudgets;
}
