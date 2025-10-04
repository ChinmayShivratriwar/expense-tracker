package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserId(UUID userId);
    Page<Transaction> findByUserId(UUID userId, Pageable pageable);
    List<Transaction> findByUserIdAndType(UUID userId, String type);
    List<Transaction> findByUserIdAndCategory(UUID userId, String category);
    List<Transaction> findByUserIdAndTransactionDateBetween(UUID userId, LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM Transaction t " +
            "WHERE t.user.id = :userId " +
            "AND t.category = :category " +
            "AND t.type IN ('EXPENSE', 'TRANSFER') " +
            "AND YEAR(t.transactionDate) = :year " +
            "AND MONTH(t.transactionDate) = :month")
    BigDecimal getTotalSpentByCategoryAndMonth(UUID userId, String category, Short month, Short year);

    long count();

}

