package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateCategoryRequest {
    private String name;
    private String type; // "INCOME" or "EXPENSE"
}
