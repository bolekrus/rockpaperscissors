package com.example.rockpaperscissors.controller;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.repository.GameStatsRepository;
import com.example.rockpaperscissors.repository.UserRepository;
import com.example.rockpaperscissors.service.GameStatsService;
import com.example.rockpaperscissors.service.RandomMoveService;
import com.example.rockpaperscissors.service.impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameServiceImpl gameService;
    private final RandomMoveService randomMoveService;
    private final GameStatsService gameStatsService;
    private final UserRepository userRepository;
    private final GameStatsRepository gameStatsRepository;

    @GetMapping("/game")
    public String game(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "redirect:/login";
        }

        User user = optionalUser.get();
        Optional<GameStats> optionalGameStats = gameStatsRepository.findByUser(user);

        GameStats gameStats = optionalGameStats.orElseGet(() -> gameStatsService.createNewStatsForUser(user));

        model.addAttribute("username", username);
        model.addAttribute("stats", gameStats);

        return "game";
    }

}
