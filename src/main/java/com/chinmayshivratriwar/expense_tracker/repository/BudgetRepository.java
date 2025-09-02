package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserIdAndCategoryAndMonthAndYear(
            UUID userId, String category, Short month, Short year);

    List<Budget> findByUserIdAndMonthAndYear(UUID userId, Short month, Short year);
    List<Budget> findAllByUserId(UUID userId);
    void deleteByUserIdAndCategoryAndMonthAndYear(UUID userId, String category, Short month, Short year);
}

