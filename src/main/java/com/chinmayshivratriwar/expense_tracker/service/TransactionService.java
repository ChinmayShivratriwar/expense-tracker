package com.chinmayshivratriwar.expense_tracker.service;


import com.chinmayshivratriwar.expense_tracker.dto.PagedResponse;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionRequest;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(UUID userId, TransactionRequest request);

    TransactionResponse updateTransaction(UUID userId, UUID transactionId, TransactionRequest request);

    void deleteTransaction(UUID userId, UUID transactionId);

    TransactionResponse getTransactionById(UUID userId, UUID transactionId);

    List<TransactionResponse> getAllTransactions(UUID userId);

    PagedResponse<TransactionResponse> getAllTransactionsForPage(UUID userId, int page, int size);

    List<TransactionResponse> getTransactionsByCategory(UUID userId, UUID categoryId);

    List<TransactionResponse> getTransactionsByCategory(UUID userId, String category);

    List<TransactionResponse> getTransactionsByDateRange(UUID userId, String startDate, String endDate);

    long getTotalTransactions();
}

