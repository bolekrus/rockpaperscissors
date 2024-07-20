package com.example.rockpaperscissors.controller;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.enums.Move;
import com.example.rockpaperscissors.enums.Result;
import com.example.rockpaperscissors.service.GameStatsService;
import com.example.rockpaperscissors.service.UserService;
import com.example.rockpaperscissors.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameControllerTest {

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private GameStatsService gameStatsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    public void testGame_UserNotFound() throws Exception {
        when(userService.getCurrentUser()).thenReturn(Optional.empty());

        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "User not found. Please login again."));
    }

    @Test
    public void testPlayGame_UserNotFound() throws Exception {
        when(userService.getCurrentUser()).thenReturn(Optional.empty());

        mockMvc.perform(post("/game/play")
                        .param("move", "ROCK"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "User not found. Please login again."));
    }

    @Test
    public void testPlayGame_UserFound() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        GameStats gameStats = new GameStats();
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(gameStatsService.getCurrentUserStats(user)).thenReturn(gameStats);
        when(gameService.getRandomMove()).thenReturn(Move.SCISSORS);
        when(gameService.getResult(Move.ROCK, Move.SCISSORS)).thenReturn(Result.WIN);

        mockMvc.perform(post("/game/play")
                        .param("move", "ROCK"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attribute("username", "testuser"))
                .andExpect(model().attribute("stats", gameStats))
                .andExpect(model().attribute("playerMove", Move.ROCK))
                .andExpect(model().attribute("computerMove", Move.SCISSORS))
                .andExpect(model().attribute("result", Result.WIN));

        verify(gameStatsService).updateGameStats(gameStats, Result.WIN);
    }
}