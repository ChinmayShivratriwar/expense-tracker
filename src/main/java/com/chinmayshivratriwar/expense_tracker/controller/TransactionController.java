package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.dto.PagedResponse;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionRequest;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionResponse;
import com.chinmayshivratriwar.expense_tracker.entities.Session;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.SessionService;
import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import com.chinmayshivratriwar.expense_tracker.util.ControllerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    //TODO: Implement Server Side Pagination and Sorting based on various params for heavy dataset
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


//
//    // Get Transaction by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
//        Session session = sessionRepository.findByRefreshToken(refreshToken)
//                .orElseThrow(() -> new RuntimeException("Session not found or already logged out"));
//        TransactionResponse response = transactionService.getTransactionById(id);
//        return ResponseEntity.ok(response);
//    }
//
//    // Update Transaction
//    @PutMapping("/{id}")
//    public ResponseEntity<TransactionResponse> updateTransaction(
//            @PathVariable Long id,
//            @Valid @RequestBody TransactionRequest request) {
//        TransactionResponse response = transactionService.updateTransaction(id, request);
//        return ResponseEntity.ok(response);
//    }
//
//    // Delete Transaction
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
//        transactionService.deleteTransaction(id);
//        return ResponseEntity.noContent().build();
//    }

    private User getUserFromToken(String authHeader){
        String accessToken = ControllerUtil.getAccessToken(authHeader);
        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }
        String username = jwtUtil.extractUsername(accessToken);
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
