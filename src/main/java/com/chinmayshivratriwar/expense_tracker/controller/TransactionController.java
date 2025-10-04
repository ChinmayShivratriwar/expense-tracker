package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.dto.*;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import com.chinmayshivratriwar.expense_tracker.util.ControllerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // Create Transaction
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request, @RequestHeader(Constant.AUTHORIZATION) String authHeader) {
        User user = getUserFromToken(authHeader);
        if(null == user) throw new RuntimeException("Token is null or empty");
        TransactionResponse response = transactionService.createTransaction(user.getId(), request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Transactions
    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(@RequestHeader(Constant.AUTHORIZATION) String authHeader) {
        User user = getUserFromToken(authHeader);
        if(null == user) throw new RuntimeException("Token is null or empty");
        List<TransactionResponse> responses = transactionService.getAllTransactions(user.getId());
        return ResponseEntity.ok(responses);
    }

    // Get All Transactions for a given page
    @GetMapping
    public ResponseEntity<PagedResponse<TransactionResponse>> getAllTransactions(
            @RequestHeader(Constant.AUTHORIZATION) String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        User user = getUserFromToken(authHeader);
        if (user == null) throw new RuntimeException("Token is null or empty");

        PagedResponse<TransactionResponse> responses = transactionService.getAllTransactionsForPage(user.getId(), page, size);
        return ResponseEntity.ok(responses);
    }


    private User getUserFromToken(String authHeader){
        String accessToken = ControllerUtil.getAccessToken(authHeader);
        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }
        String username = jwtUtil.extractUsername(accessToken);
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("spends-per-category")
    public ResponseEntity<UserCategorySpendResponse> getUserCategorySpend(@RequestHeader(Constant.AUTHORIZATION) String authHeader){
        User user = getUserFromToken(authHeader);
        if(null == user) throw new RuntimeException("Token is null or empty");
        Map<String, BigDecimal> categorySpendMap = transactionService.getUserSpendsPerCategory(user.getId());
        List<CategorySpend> categorySpends = new ArrayList<>();
        categorySpendMap.forEach(
                (category, totalAmount) -> {
                    categorySpends.add(
                            CategorySpend.builder()
                                    .totalAmount(totalAmount)
                                    .category(category)
                                    .build()
                    );
                }
        );
        return new ResponseEntity<>(
                UserCategorySpendResponse.builder()
                        .categorySpend(categorySpends)
                        .userId(String.valueOf(user.getId()))
                        .build()
                , HttpStatus.OK);
    }
}
