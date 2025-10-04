package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserCategorySpendResponse {
    public String userId;
    public List<CategorySpend> categorySpend;
}
