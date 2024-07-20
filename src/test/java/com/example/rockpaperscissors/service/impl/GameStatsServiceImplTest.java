package com.example.rockpaperscissors.service.impl;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.enums.Result;
import com.example.rockpaperscissors.repository.GameStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameStatsServiceImplTest {

    @Mock
    private GameStatsRepository gameStatsRepository;

    @InjectMocks
    private GameStatsServiceImpl gameStatsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewStatsForUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        GameStats gameStats = new GameStats();
        gameStats.setUser(user);
        gameStats.setGamesWin(0);
        gameStats.setGamesLose(0);
        gameStats.setGamesTied(0);
        gameStats.setTotalGames(0);

        when(gameStatsRepository.save(any(GameStats.class))).thenReturn(gameStats);

        GameStats createdStats = gameStatsService.createNewStatsForUser(user);

        assertEquals(0, createdStats.getGamesWin());
        assertEquals(0, createdStats.getGamesLose());
        assertEquals(0, createdStats.getGamesTied());
        assertEquals(0, createdStats.getTotalGames());
        assertEquals(user, createdStats.getUser());

        verify(gameStatsRepository, times(1)).save(any(GameStats.class));
    }

    @Test
    void updateGameStatsWin() {
        GameStats gameStats = new GameStats();
        gameStats.setGamesWin(5);
        gameStats.setTotalGames(10);

        gameStatsService.updateGameStats(gameStats, Result.WIN);

        assertEquals(6, gameStats.getGamesWin());
        assertEquals(11, gameStats.getTotalGames());

        verify(gameStatsRepository, times(1)).save(gameStats);
    }

    @Test
    public void testUpdateGameStatsLose() {
        GameStats gameStats = new GameStats();
        gameStats.setGamesLose(3);
        gameStats.setTotalGames(10);

        gameStatsService.updateGameStats(gameStats, Result.LOSE);

        assertEquals(4, gameStats.getGamesLose());
        assertEquals(11, gameStats.getTotalGames());

        verify(gameStatsRepository, times(1)).save(gameStats);
    }

    @Test
    public void testUpdateGameStatsDraw() {
        GameStats gameStats = new GameStats();
        gameStats.setGamesTied(2);
        gameStats.setTotalGames(10);

        gameStatsService.updateGameStats(gameStats, Result.DRAW);

        assertEquals(3, gameStats.getGamesTied());
        assertEquals(11, gameStats.getTotalGames());

        verify(gameStatsRepository, times(1)).save(gameStats);
    }

    @Test
    public void testGetCurrentUserStatsExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        GameStats gameStats = new GameStats();
        gameStats.setUser(user);

        when(gameStatsRepository.findByUser(user)).thenReturn(Optional.of(gameStats));

        GameStats fetchedStats = gameStatsService.getCurrentUserStats(user);

        assertEquals(gameStats, fetchedStats);

        verify(gameStatsRepository, times(1)).findByUser(user);
    }

    @Test
    public void testGetCurrentUserStatsNotExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        GameStats newStats = new GameStats();
        newStats.setUser(user);
        newStats.setGamesWin(0);
        newStats.setGamesLose(0);
        newStats.setGamesTied(0);
        newStats.setTotalGames(0);

        when(gameStatsRepository.findByUser(user)).thenReturn(Optional.empty());
        when(gameStatsRepository.save(any(GameStats.class))).thenReturn(newStats);

        GameStats fetchedStats = gameStatsService.getCurrentUserStats(user);

        assertEquals(newStats, fetchedStats);
        assertEquals(user, fetchedStats.getUser());

        verify(gameStatsRepository, times(1)).findByUser(user);
        verify(gameStatsRepository, times(1)).save(any(GameStats.class));
    }

}