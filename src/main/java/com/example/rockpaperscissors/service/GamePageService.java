package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GamePageService {

    private final UserRepository userRepository;
    private final GameStatsService gameStatsService;

    public String loadGamePage(Model model) {
        Optional<User> optionalUser = getCurrentUser();
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found. Please login again.");
            return "login";
        }

        User user = optionalUser.get();
        GameStats gameStats = gameStatsService.getCurrentUserStats(user);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("stats", gameStats);

        return "game";
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
}
