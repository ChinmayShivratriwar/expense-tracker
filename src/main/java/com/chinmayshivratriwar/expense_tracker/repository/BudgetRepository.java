//package com.chinmayshivratriwar.expense_tracker.repository;
//
//import com.chinmayshivratriwar.expense_tracker.entities.Budget;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface BudgetRepository extends JpaRepository<Budget, Long> {
//
//    Optional<Budget> findByUser_IdAndCategoryAndMonthAndYear(
//            UUID userId, String category, Integer month, Integer year);
//
//    List<Budget> findByUser_IdAndMonthAndYear(UUID userId, Integer month, Integer year);
//}
//
