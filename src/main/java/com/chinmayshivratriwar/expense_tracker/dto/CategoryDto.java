package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CategoryDto {
    private UUID id;
    private String name;
    private String type; // "INCOME" or "EXPENSE"
}

