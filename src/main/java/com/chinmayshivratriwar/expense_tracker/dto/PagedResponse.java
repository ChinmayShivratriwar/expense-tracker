package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Data;
import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    public PagedResponse(List<T> content, int page, int size, long totalItems, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}

