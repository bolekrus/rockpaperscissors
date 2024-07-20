package com.example.rockpaperscissors.controller;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.enums.Move;
import com.example.rockpaperscissors.enums.Result;
import com.example.rockpaperscissors.service.GameStatsService;
import com.example.rockpaperscissors.service.MyUserDetailsService;
import com.example.rockpaperscissors.service.impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameServiceImpl gameService;
    private final GameStatsService gameStatsService;
    private final MyUserDetailsService userDetailsService;
    private final MyUserDetailsService myUserDetailsService;

    @GetMapping("/game")
    public String game(Model model) {
        Optional<User> optionalUser = userDetailsService.getCurrentUser();
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

    @PostMapping("/game/play")
    public String playGame(@RequestParam("move") String move, Model model) {
        Optional<User> optionalUser = myUserDetailsService.getCurrentUser();
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found. Please login again.");
            return "login";
        }

        User user = optionalUser.get();
        Move playerMove = Move.valueOf(move.toUpperCase());
        Move computerMove = gameService.getRandomMove();
        Result result = gameService.getResult(playerMove, computerMove);

        GameStats gameStats = gameStatsService.getCurrentUserStats(user);
        gameStatsService.updateGameStats(gameStats, result);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("stats", gameStats);
        model.addAttribute("playerMove", playerMove);
        model.addAttribute("computerMove", computerMove);
        model.addAttribute("result", result);

        return "game";
    }
}


