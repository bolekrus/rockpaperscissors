package com.example.rockpaperscissors.service.impl;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.enums.Result;
import com.example.rockpaperscissors.repository.GameStatsRepository;
import com.example.rockpaperscissors.service.GameStatsService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
public class GameStatsServiceImpl implements GameStatsService {

    private final GameStatsRepository gameStatsRepository;

    public GameStatsServiceImpl(GameStatsRepository gameStatsRepository) {
        this.gameStatsRepository = gameStatsRepository;
    }

    @Override
    public GameStats createNewStatsForUser(User user) {
        GameStats stats = new GameStats();
        stats.setUser(user);
        stats.setGamesWin(0);
        stats.setGamesLose(0);
        stats.setGamesTied(0);
        stats.setTotalGames(0);
        return gameStatsRepository.save(stats);
    }

    @Override
    public void updateGameStats(GameStats gameStats, Result result) {
        Map<Result, Consumer<GameStats>> updateStrategies = Map.of(
                Result.WIN, stats -> stats.setGamesWin(stats.getGamesWin() + 1),
                Result.LOSE, stats -> stats.setGamesLose(stats.getGamesLose() + 1),
                Result.DRAW, stats -> stats.setGamesTied(stats.getGamesTied() + 1)
        );

        updateStrategies.get(result).accept(gameStats);
        gameStats.setTotalGames(gameStats.getTotalGames() + 1);
    }
}