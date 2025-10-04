package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.TopThreeCategory;
import com.chinmayshivratriwar.expense_tracker.entities.UserCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnalysticsRepository extends JpaRepository<TopThreeCategory, UserCategoryId> {
    @Query(
            value = "SELECT * FROM gold_user_top_three_category WHERE user_id = :userId",
            nativeQuery = true
    )
    List<TopThreeCategory> findAllByUserId(@Param("userId") String userId);
}
