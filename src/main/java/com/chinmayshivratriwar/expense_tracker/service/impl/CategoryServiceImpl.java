package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.dto.CategoryDto;
import com.chinmayshivratriwar.expense_tracker.dto.CreateCategoryRequest;
import com.chinmayshivratriwar.expense_tracker.entities.Category;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.enums.CategoryType;
import com.chinmayshivratriwar.expense_tracker.repository.CategoryRepository;
import com.chinmayshivratriwar.expense_tracker.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(User user, CreateCategoryRequest request) {
        Category category = Category.builder()
                .user(user)
                .name(request.getName())
                .type(CategoryType.valueOf(request.getType().toUpperCase()))
                .build();

        Category saved = categoryRepository.save(category);

        return mapToDto(saved);
    }

    @Override
    public List<CategoryDto> getCategories(User user) {
        return categoryRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType().name())
                .build();
    }
}
