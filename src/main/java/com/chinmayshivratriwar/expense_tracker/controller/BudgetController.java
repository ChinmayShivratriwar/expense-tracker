package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.dto.BudgetRequest;
import com.chinmayshivratriwar.expense_tracker.dto.BudgetResponse;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.BudgetService;
import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import com.chinmayshivratriwar.expense_tracker.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BudgetController {

    private final BudgetService budgetService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BudgetResponse> createOrUpdateBudget(
            @RequestBody BudgetRequest request,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        BudgetResponse response = budgetService.createOrUpdateBudget(user.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgetForUser(
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        List<BudgetResponse> responses = budgetService.getAllBudgetsForUser(user.getId());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{category}/{month}/{year}")
    public ResponseEntity<BudgetResponse> getBudget(
            @PathVariable String category,
            @PathVariable Short month,
            @PathVariable Short year,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        BudgetResponse response = budgetService.getBudget(user.getId(), category, month, year);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsForMonth(
            @PathVariable Short month,
            @PathVariable Short year,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        List<BudgetResponse> responses = budgetService.getBudgetsForMonth(user.getId(), month, year);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        budgetService.deleteBudget(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{category}/{month}/{year}/add-expense")
    public ResponseEntity<BudgetResponse> addExpense(
            @PathVariable String category,
            @PathVariable Short month,
            @PathVariable Short year,
            @RequestParam BigDecimal amount,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        BudgetResponse response = budgetService.addExpense(user.getId(), category, amount, month, year);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{category}/{month}/{year}/remove-budget")
    public ResponseEntity<Void> removeBudget(
            @PathVariable String category,
            @PathVariable Short month,
            @PathVariable Short year,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);

        budgetService.removeBudget(user.getId(), category, month, year);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{category}/{month}/{year}/is-over")
    public ResponseEntity<Boolean> isOverBudget(
            @PathVariable String category,
            @PathVariable Short month,
            @PathVariable Short year,
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User user = getUserFromToken(authHeader);
        boolean overBudget = budgetService.isOverBudget(user.getId(), category, month, year);
        return ResponseEntity.ok(overBudget);
    }

    private User getUserFromToken(String authHeader){
        String accessToken = ControllerUtil.getAccessToken(authHeader);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Missing or invalid token");
        }
        String username = jwtUtil.extractUsername(accessToken);
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
