package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.dto.TransactionRequest;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionResponse;
import com.chinmayshivratriwar.expense_tracker.entities.Transaction;
import com.chinmayshivratriwar.expense_tracker.repository.TransactionRepository;
import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionResponse createTransaction(UUID userId, TransactionRequest request) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setCategory(request.getCategory()); // string category

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponseDto(saved);
    }

    @Override
    public TransactionResponse updateTransaction(UUID userId, UUID transactionId, TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to update this transaction");
        }

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setCategory(request.getCategory()); // update category string

        return mapToResponseDto(transactionRepository.save(transaction));
    }

    @Override
    public void deleteTransaction(UUID userId, UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this transaction");
        }

        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionResponse getTransactionById(UUID userId, UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to view this transaction");
        }

        return mapToResponseDto(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions(UUID userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionsByCategory(UUID userId, UUID categoryId) {
        return List.of();
    }

    @Override
    public List<TransactionResponse> getTransactionsByCategory(UUID userId, String category) {
        return transactionRepository.findByUserIdAndCategory(userId, category).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionsByDateRange(UUID userId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToResponseDto(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .category(transaction.getCategory())
                .build();
    }
}
