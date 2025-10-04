package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.dto.CategorySpend;
import com.chinmayshivratriwar.expense_tracker.dto.PagedResponse;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionRequest;
import com.chinmayshivratriwar.expense_tracker.dto.TransactionResponse;
import com.chinmayshivratriwar.expense_tracker.entities.Transaction;
import com.chinmayshivratriwar.expense_tracker.repository.BudgetRepository;
import com.chinmayshivratriwar.expense_tracker.repository.TransactionRepository;
import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
import com.chinmayshivratriwar.expense_tracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;


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
        transaction.setCategory(request.getCategory());

        Transaction saved = transactionRepository.save(transaction);
        if(Constant.EXPENSE.equalsIgnoreCase(saved.getType()) || Constant.TRANSFER.equalsIgnoreCase(saved.getType())){
            budgetRepository.findByUserIdAndCategoryAndMonthAndYear(saved.getUser().getId(), saved.getCategory(), Short.valueOf(String.valueOf(saved.getTransactionDate().getMonth().getValue())), Short.valueOf(String.valueOf(saved.getTransactionDate().getYear())))
                    .ifPresent(budget -> {
                        budget.setSpentAmount(budget.getSpentAmount().add(saved.getAmount()));
                        budgetRepository.save(budget);
                    });
        }
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
        transaction.setCategory(request.getCategory());

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


    public PagedResponse<TransactionResponse> getAllTransactionsForPage(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Transaction> transactionPage = transactionRepository.findByUserId(userId, pageable);

        List<TransactionResponse> content = transactionPage.stream()
                .map(TransactionResponse::fromEntity)
                .toList();

        return new PagedResponse<>(
                content,
                page,
                size,
                transactionPage.getTotalElements(),
                transactionPage.getTotalPages()
        );
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

    @Override
    public long getTotalTransactions() {
        return transactionRepository.count();
    }

    @Override
    //Chinmay to himself - "I Should have done this in analytics microservice,
    //such heavy queries should not be in backend, in backend it should have been only a read operation from
    //transformed tables -  this will cause unnecessary computational overhead in backend"
    public Map<String, BigDecimal> getUserSpendsPerCategory(UUID userId) {
        List<CategorySpend> sums = transactionRepository.sumSpendsPerCategory(userId);
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        Constant.CATEGORIES.forEach(cat -> result.put(cat, BigDecimal.ZERO));
        sums.forEach(cs -> result.put(cs.getCategory(), cs.getTotalAmount() != null ? cs.getTotalAmount() : BigDecimal.ZERO));
        return result;
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
