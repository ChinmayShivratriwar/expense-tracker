//package com.chinmayshivratriwar.expense_tracker.service;
//
//import com.chinmayshivratriwar.expense_tracker.dto.BudgetRequest;
//import com.chinmayshivratriwar.expense_tracker.dto.BudgetResponse;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
//public interface BudgetService {
//
//    BudgetResponse createOrUpdateBudget(UUID userId, BudgetRequest request);
//
//    BudgetResponse getBudget(UUID userId, String category, int month, int year);
//
//    List<BudgetResponse> getBudgetsForMonth(UUID userId, int month, int year);
//
//    void deleteBudget(Long budgetId, UUID userId);
//
//    BudgetResponse addExpense(UUID userId, String category, BigDecimal expenseAmount, int month, int year);
//
//    BudgetResponse removeExpense(UUID userId, String category, BigDecimal expenseAmount, int month, int year);
//
//    boolean isOverBudget(UUID userId, String category, int month, int year);
//}
