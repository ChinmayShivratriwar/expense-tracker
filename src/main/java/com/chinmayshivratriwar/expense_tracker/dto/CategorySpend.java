package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CategorySpend {
    public String category;
    public BigDecimal totalAmount;
}
