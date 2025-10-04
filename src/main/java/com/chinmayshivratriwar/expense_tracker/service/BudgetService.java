package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.dto.BudgetRequest;
import com.chinmayshivratriwar.expense_tracker.dto.BudgetResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BudgetService {

    BudgetResponse createOrUpdateBudget(UUID userId, BudgetRequest request);

    BudgetResponse getBudget(UUID userId, String category, Short month, Short year);

    List<BudgetResponse> getAllBudgetsForUser(UUID userId);

    List<BudgetResponse> getBudgetsForMonth(UUID userId, Short month, Short year);

    void deleteBudget(Long budgetId, UUID userId);

    BudgetResponse addExpense(UUID userId, String category, BigDecimal expenseAmount, Short month, Short year);

    void removeBudget(UUID userId, String category, Short month, Short year);

    boolean isOverBudget(UUID userId, String category, Short month, Short year);

    long getTotalBudgets();
}
