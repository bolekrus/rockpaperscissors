package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.enums.Result;

public interface GameStatsService {
    GameStats createNewStatsForUser(User user);
    void updateGameStats(GameStats gameStats, Result result);
}
