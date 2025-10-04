package com.chinmayshivratriwar.expense_tracker.dto;

import com.chinmayshivratriwar.expense_tracker.entities.TopThreeCategory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Data
public class UserAnalyticsResponse {
    String userId;
    List<TopThreeCategory> top3Categories;
}
