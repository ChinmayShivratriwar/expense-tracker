package com.chinmayshivratriwar.expense_tracker.controller;


import com.chinmayshivratriwar.expense_tracker.dto.CategoryDto;
import com.chinmayshivratriwar.expense_tracker.dto.CreateCategoryRequest;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CreateCategoryRequest request, Principal principal) {
        // TODO: lookup User from principal/session
        User user = getUserFromPrincipal(principal);
        return categoryService.createCategory(user, request);
    }

    @GetMapping
    public List<CategoryDto> getCategories(Principal principal) {
        User user = getUserFromPrincipal(principal);
        return categoryService.getCategories(user);
    }

    private User getUserFromPrincipal(Principal principal) {
        // Placeholder – later replace with real lookup from session or JWT
        User u = new User();
        u.setId(UUID.randomUUID());
        return u;
    }
}

