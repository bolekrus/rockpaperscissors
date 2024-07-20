package com.example.rockpaperscissors.service.impl;

import com.example.rockpaperscissors.enums.Move;
import com.example.rockpaperscissors.enums.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    private GameServiceImpl gameService;
    private Random mockRandom;

    @BeforeEach
    public void setUp() {
        mockRandom = mock(Random.class);
        gameService = new GameServiceImpl();
    }

    @Test
    void getResult() {
        assertEquals(Result.DRAW, gameService.getResult(Move.ROCK, Move.ROCK));
        assertEquals(Result.LOSE, gameService.getResult(Move.ROCK, Move.PAPER));
        assertEquals(Result.WIN, gameService.getResult(Move.ROCK, Move.SCISSORS));

        assertEquals(Result.WIN, gameService.getResult(Move.PAPER, Move.ROCK));
        assertEquals(Result.DRAW, gameService.getResult(Move.PAPER, Move.PAPER));
        assertEquals(Result.LOSE, gameService.getResult(Move.PAPER, Move.SCISSORS));

        assertEquals(Result.LOSE, gameService.getResult(Move.SCISSORS, Move.ROCK));
        assertEquals(Result.WIN, gameService.getResult(Move.SCISSORS, Move.PAPER));
        assertEquals(Result.DRAW, gameService.getResult(Move.SCISSORS, Move.SCISSORS));
    }

    @Test
    void getRandomMove() {
        when(mockRandom.nextInt(Move.values().length)).thenReturn(0, 1, 2);

        GameServiceImpl gameServiceWithMockRandom = new GameServiceImpl() {
            @Override
            public Move getRandomMove() {
                Move[] moves = Move.values();
                return moves[mockRandom.nextInt(moves.length)];
            }
        };

        assertEquals(Move.ROCK, gameServiceWithMockRandom.getRandomMove());
        assertEquals(Move.PAPER, gameServiceWithMockRandom.getRandomMove());
        assertEquals(Move.SCISSORS, gameServiceWithMockRandom.getRandomMove());

        verify(mockRandom, times(3)).nextInt(Move.values().length);
    }
}