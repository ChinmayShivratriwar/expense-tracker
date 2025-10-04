package com.chinmayshivratriwar.expense_tracker.entities;

import java.io.Serializable;
import java.util.Objects;

public class UserCategoryId implements Serializable {
    private String userId;
    private String category;

    public UserCategoryId() {}

    public UserCategoryId(String userId, String category) {
        this.userId = userId;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCategoryId)) return false;
        UserCategoryId that = (UserCategoryId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, category);
    }
}

