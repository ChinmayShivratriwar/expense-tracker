//package com.chinmayshivratriwar.expense_tracker.service.impl;
//
//import com.chinmayshivratriwar.expense_tracker.dto.BudgetRequest;
//import com.chinmayshivratriwar.expense_tracker.dto.BudgetResponse;
//import com.chinmayshivratriwar.expense_tracker.entities.Budget;
//import com.chinmayshivratriwar.expense_tracker.entities.User;
//import com.chinmayshivratriwar.expense_tracker.repository.BudgetRepository;
//import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
//import com.chinmayshivratriwar.expense_tracker.service.BudgetService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class BudgetServiceImpl implements BudgetService {
//
//    private final BudgetRepository budgetRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public BudgetResponse createOrUpdateBudget(UUID userId, BudgetRequest request) {
//        Budget budget = budgetRepository
//                .findByUser_IdAndCategoryAndMonthAndYear(userId, request.getCategory(), request.getMonth(), request.getYear())
//                .orElseGet(() -> {
//                    User user = userRepository.findById(userId)
//                            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
//                    return Budget.builder()
//                            .user(user)
//                            .category(request.getCategory())
//                            .month(request.getMonth())
//                            .year(request.getYear())
//                            .spentAmount(BigDecimal.ZERO)
//                            .build();
//                });
//
//        budget.setLimitAmount(request.getLimitAmount());
//        return BudgetResponse.fromEntity(budgetRepository.save(budget));
//    }
//
//    @Override
//    public BudgetResponse getBudget(UUID userId, String category, int month, int year) {
//        Budget budget = budgetRepository.findByUser_IdAndCategoryAndMonthAndYear(userId, category, month, year)
//                .orElseThrow(() -> new RuntimeException("Budget not found for category " + category));
//        return BudgetResponse.fromEntity(budget);
//    }
//
//    @Override
//    public List<BudgetResponse> getBudgetsForMonth(UUID userId, int month, int year) {
//        return budgetRepository.findByUser_IdAndMonthAndYear(userId, month, year)
//                .stream()
//                .map(BudgetResponse::fromEntity)
//                .toList();
//    }
//
//    @Override
//    public void deleteBudget(Long budgetId, UUID userId) {
//        Budget budget = budgetRepository.findById(budgetId)
//                .orElseThrow(() -> new RuntimeException("Budget not found: " + budgetId));
//
//        if (!budget.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized to delete this budget");
//        }
//        budgetRepository.delete(budget);
//    }
//
//    @Override
//    public BudgetResponse addExpense(UUID userId, String category, BigDecimal expenseAmount, int month, int year) {
//        Budget budget = budgetRepository.findByUser_IdAndCategoryAndMonthAndYear(userId, category, month, year)
//                .orElseThrow(() -> new RuntimeException("No budget set for category " + category));
//
//        budget.setSpentAmount(budget.getSpentAmount().add(expenseAmount));
//        return BudgetResponse.fromEntity(budgetRepository.save(budget));
//    }
//
//    @Override
//    public BudgetResponse removeExpense(UUID userId, String category, BigDecimal expenseAmount, int month, int year) {
//        Budget budget = budgetRepository.findByUser_IdAndCategoryAndMonthAndYear(userId, category, month, year)
//                .orElseThrow(() -> new RuntimeException("No budget set for category " + category));
//
//        budget.setSpentAmount(budget.getSpentAmount().subtract(expenseAmount));
//        if (budget.getSpentAmount().compareTo(BigDecimal.ZERO) < 0) {
//            budget.setSpentAmount(BigDecimal.ZERO);
//        }
//        return BudgetResponse.fromEntity(budgetRepository.save(budget));
//    }
//
//    @Override
//    public boolean isOverBudget(UUID userId, String category, int month, int year) {
//        return budgetRepository.findByUser_IdAndCategoryAndMonthAndYear(userId, category, month, year)
//                .map(b -> b.getSpentAmount().compareTo(b.getLimitAmount()) > 0)
//                .orElse(false);
//    }
//}
