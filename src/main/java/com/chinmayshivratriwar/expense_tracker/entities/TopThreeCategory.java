package com.chinmayshivratriwar.expense_tracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@IdClass(UserCategoryId.class)
@Table(name = "gold_user_top_three_category")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class TopThreeCategory {

    @Id
    private String userId;

    @Id
    private String category;

    private Double totalAmount;
    private Integer txnCount;
    private Float rank;
}

