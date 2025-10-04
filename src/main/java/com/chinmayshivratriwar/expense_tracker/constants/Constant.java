package com.chinmayshivratriwar.expense_tracker.constants;

import java.util.Set;

public class Constant {
    private Constant(){

    }

    public static final String EMPTY_STRING = "";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String EXPENSE = "EXPENSE";
    public static final String TRANSFER = "TRANSFER";
    public static final String ADMIN = "ADMIN";
    public static final Set<String> CATEGORIES = Set.of(
            "Food", "Leisure", "Vehicle",
            "Subscription", "Shopping",
            "Bills", "Entertainment",
            "Travel", "Health",
            "Education", "Others"
    );
}
