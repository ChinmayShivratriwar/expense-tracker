//package com.chinmayshivratriwar.expense_tracker.entities;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.math.BigDecimal;
//
//@Entity
//@Table(
//        name = "budgets",
//        uniqueConstraints = @UniqueConstraint(
//                name = "uq_budget_user_cat_month_year",
//                columnNames = {"user_id", "category", "month", "year"}
//        ),
//        indexes = {
//                @Index(name = "idx_budgets_user_month_year_cat", columnList = "user_id, year, month, category")
//        }
//)
//@Getter @Setter
//@Builder
//public class Budget {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(nullable = false, length = 64)
//    private String category;
//
//    @Column(name = "limit_amount", nullable = false, precision = 14, scale = 2)
//    private BigDecimal limitAmount;
//
//    @Column(name = "spent_amount", nullable = false, precision = 14, scale = 2)
//    private BigDecimal spentAmount = BigDecimal.ZERO;
//
//    @Column(nullable = false)
//    private Integer month;
//
//    @Column(nullable = false)
//    private Integer year;
//
//    @Column(name = "created_at", nullable = false)
//    private java.time.OffsetDateTime createdAt = java.time.OffsetDateTime.now();
//
//    @Column(name = "updated_at", nullable = false)
//    private java.time.OffsetDateTime updatedAt = java.time.OffsetDateTime.now();
//
//    @PreUpdate
//    public void touchUpdateTime() {
//        this.updatedAt = java.time.OffsetDateTime.now();
//    }
//}
