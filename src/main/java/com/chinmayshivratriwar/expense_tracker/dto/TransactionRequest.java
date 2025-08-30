package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionRequest {
    private BigDecimal amount;
    private String type; // INCOME / EXPENSE / TRANSFER
    private String category;
    private String description;
    private LocalDateTime transactionDate;
}
