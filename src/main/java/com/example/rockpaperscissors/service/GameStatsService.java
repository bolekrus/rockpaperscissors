package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;

public interface GameStatsService {
    GameStats createNewStatsForUser(User user);
}
