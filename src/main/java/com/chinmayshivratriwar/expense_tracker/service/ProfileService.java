package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.dto.ProfileResponse;
import com.chinmayshivratriwar.expense_tracker.entities.Profile;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    Profile createProfile(Profile profile);
    ProfileResponse getProfileByUserId(String userId);
}

