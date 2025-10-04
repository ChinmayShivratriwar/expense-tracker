package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.entities.TopThreeCategory;
import com.chinmayshivratriwar.expense_tracker.repository.UserAnalysticsRepository;
import com.chinmayshivratriwar.expense_tracker.service.UserAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnalyticsServiceImpl implements UserAnalyticsService {

    private final UserAnalysticsRepository userAnalysticsRepository;

    @Override
    public List<TopThreeCategory> getTopThreeCategoriesPerUser(String userId) {
        return userAnalysticsRepository.findAllByUserId(userId);
    }
}
