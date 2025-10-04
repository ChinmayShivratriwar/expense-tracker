package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.dto.AdminStatsResponse;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.BudgetService;
import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import com.chinmayshivratriwar.expense_tracker.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final TransactionService transactionService;
    private final BudgetService budgetService;

    @GetMapping("stats")
    public ResponseEntity<AdminStatsResponse> getAllUsers(
            @RequestHeader(Constant.AUTHORIZATION) String authHeader) {

        User requester = getUserFromToken(authHeader);

        // Allow only admins
        if (!Constant.ADMIN.equalsIgnoreCase(requester.getRole().name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AdminStatsResponse adminStatsResponse = AdminStatsResponse.builder()
                .totalUsers(String.valueOf(userService.getTotalUsers()))
                .totalBudgets(String.valueOf(budgetService.getTotalBudgets()))
                .totalTransactions(String.valueOf(transactionService.getTotalTransactions()))
                .build();
        return ResponseEntity.ok(adminStatsResponse);
    }

    private User getUserFromToken(String authHeader) {
        String accessToken = ControllerUtil.getAccessToken(authHeader);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Invalid or missing token");
        }
        String username = jwtUtil.extractUsername(accessToken);
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
