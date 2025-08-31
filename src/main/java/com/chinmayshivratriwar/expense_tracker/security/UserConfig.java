package com.chinmayshivratriwar.expense_tracker.security;

import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return usernameOrEmail -> {
            User user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return new UserPrincipal(user); // <-- wrap entity in UserDetails
        };
    }

}
