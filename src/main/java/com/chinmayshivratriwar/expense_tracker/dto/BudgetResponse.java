//package com.chinmayshivratriwar.expense_tracker.dto;
//
//import com.chinmayshivratriwar.expense_tracker.entities.Budget;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.OffsetDateTime;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class BudgetResponse {
//    private Long id;
//    private String category;
//    private BigDecimal limitAmount;
//    private BigDecimal spentAmount;
//    private BigDecimal remainingAmount;
//    private Double percentageUsed;
//    private Integer month;
//    private Integer year;
//    private String status;
//    private OffsetDateTime createdAt;
//    private OffsetDateTime updatedAt;
//
//    public static BudgetResponse fromEntity(Budget b) {
//        BigDecimal limit = b.getLimitAmount() == null ? BigDecimal.ZERO : b.getLimitAmount();
//        BigDecimal spent = b.getSpentAmount() == null ? BigDecimal.ZERO : b.getSpentAmount();
//        BigDecimal remaining = limit.subtract(spent);
//        double pct = 0.0;
//        if (limit.compareTo(BigDecimal.ZERO) > 0) {
//            pct = spent.multiply(BigDecimal.valueOf(100)).divide(limit, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
//        String status = remaining.compareTo(BigDecimal.ZERO) >= 0 ? "UNDER_BUDGET" : "OVER_BUDGET";
//
//        return BudgetResponse.builder()
//                .id(b.getId())
//                .category(b.getCategory())
//                .limitAmount(limit)
//                .spentAmount(spent)
//                .remainingAmount(remaining)
//                .percentageUsed(pct)
//                .month(b.getMonth())
//                .year(b.getYear())
//                .status(status)
//                .createdAt(b.getCreatedAt())
//                .updatedAt(b.getUpdatedAt())
//                .build();
//    }
//}
