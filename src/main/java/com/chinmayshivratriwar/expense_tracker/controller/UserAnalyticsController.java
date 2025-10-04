package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;
import com.chinmayshivratriwar.expense_tracker.dto.UserAnalyticsResponse;
import com.chinmayshivratriwar.expense_tracker.entities.TopThreeCategory;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.UserAnalyticsService;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import com.chinmayshivratriwar.expense_tracker.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserAnalyticsController {

    private final UserAnalyticsService userAnalyticsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/top3")
    public ResponseEntity<UserAnalyticsResponse> getTopThreeCategories(@RequestHeader(Constant.AUTHORIZATION) String authHeader){
        User user = getUserFromToken(authHeader);
        if (user == null) throw new RuntimeException("Token is null or empty");
        List<TopThreeCategory> output = userAnalyticsService.getTopThreeCategoriesPerUser(String.valueOf(user.getId()));
        return new ResponseEntity<>(UserAnalyticsResponse.builder()
                .userId(String.valueOf(user.getId()))
                .top3Categories(output)
                .build(), HttpStatus.OK);
    }

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
