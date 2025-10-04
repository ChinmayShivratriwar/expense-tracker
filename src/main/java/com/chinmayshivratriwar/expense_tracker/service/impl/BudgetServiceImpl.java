package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.dto.BudgetRequest;
import com.chinmayshivratriwar.expense_tracker.dto.BudgetResponse;
import com.chinmayshivratriwar.expense_tracker.entities.Budget;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.repository.BudgetRepository;
import com.chinmayshivratriwar.expense_tracker.repository.TransactionRepository;
import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
import com.chinmayshivratriwar.expense_tracker.service.BudgetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BudgetResponse createOrUpdateBudget(UUID userId, BudgetRequest request) {
        BigDecimal spentAmount = transactionRepository.getTotalSpentByCategoryAndMonth(userId, request.getCategory(), request.getMonth(), request.getYear());
        Budget budget = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYear(userId, request.getCategory(), request.getMonth(), request.getYear())
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
                    return Budget.builder()
                            .userId(user.getId())
                            .category(request.getCategory())
                            .month(request.getMonth())
                            .year(request.getYear())
                            .spentAmount(spentAmount)
                            .build();
                });

        budget.setLimitAmount(request.getLimitAmount());
        return BudgetResponse.fromEntity(budgetRepository.save(budget));
    }

    @Override
    public BudgetResponse getBudget(UUID userId, String category, Short month, Short year) {
        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(userId, category, month, year)
                .orElseThrow(() -> new RuntimeException("Budget not found for category " + category));
        return BudgetResponse.fromEntity(budget);
    }

    @Override
    public List<BudgetResponse> getAllBudgetsForUser(UUID userId) {
        return budgetRepository.findAllByUserId(userId)
                .stream()
                .map(BudgetResponse::fromEntity)
                .toList();
    }

    @Override
    public List<BudgetResponse> getBudgetsForMonth(UUID userId, Short month, Short year) {
        return budgetRepository.findByUserIdAndMonthAndYear(userId, month, year)
                .stream()
                .map(BudgetResponse::fromEntity)
                .toList();
    }

    @Override
    public void deleteBudget(Long budgetId, UUID userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found: " + budgetId));

        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this budget");
        }
        budgetRepository.delete(budget);
    }

    @Override
    public BudgetResponse addExpense(UUID userId, String category, BigDecimal expenseAmount, Short month, Short year) {
        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(userId, category, month, year)
                .orElseThrow(() -> new RuntimeException("No budget set for category " + category));

        budget.setSpentAmount(budget.getSpentAmount().add(expenseAmount));
        return BudgetResponse.fromEntity(budgetRepository.save(budget));
    }

    @Override
    public void removeBudget(UUID userId, String category, Short month, Short year) {
        Optional<Budget> budgetOpt = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYear(userId, category, month, year);

        if (budgetOpt.isEmpty()) {
            throw new RuntimeException("Budget not found for category " + category + " in " + month + "/" + year);
        }

        budgetRepository.deleteByUserIdAndCategoryAndMonthAndYear(userId, category, month, year);
    }

    @Override
    public boolean isOverBudget(UUID userId, String category, Short month, Short year) {
        return budgetRepository.findByUserIdAndCategoryAndMonthAndYear(userId, category, month, year)
                .map(b -> b.getSpentAmount().compareTo(b.getLimitAmount()) > 0)
                .orElse(false);
    }

    @Override
    public long getTotalBudgets() {
        return budgetRepository.count();
    }
}
