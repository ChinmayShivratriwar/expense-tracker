package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.dto.CategoryDto;
import com.chinmayshivratriwar.expense_tracker.dto.CreateCategoryRequest;
import com.chinmayshivratriwar.expense_tracker.entities.User;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(User user, CreateCategoryRequest request);
    List<CategoryDto> getCategories(User user);
}

