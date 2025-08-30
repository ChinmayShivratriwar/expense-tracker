package com.chinmayshivratriwar.expense_tracker.controller;

//import com.chinmayshivratriwar.expense_tracker.dto.TransactionRequest;
//import com.chinmayshivratriwar.expense_tracker.dto.TransactionResponse;
//import com.chinmayshivratriwar.expense_tracker.entities.Session;
//import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/transactions")
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    // Create Transaction
//    @PostMapping
//    public ResponseEntity<TransactionResponse> createTransaction(
//            @Valid @RequestBody TransactionRequest request) {
//        TransactionResponse response = transactionService.createTransaction(request);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    // Get All Transactions
//    @GetMapping
//    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
//        List<TransactionResponse> responses = transactionService.getAllTransactions();
//        return ResponseEntity.ok(responses);
//    }
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
//}
