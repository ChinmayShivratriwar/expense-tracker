package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.entities.TopThreeCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAnalyticsService {
    List<TopThreeCategory> getTopThreeCategoriesPerUser(String userId);
}
