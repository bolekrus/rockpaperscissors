package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.enums.Move;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomMoveService {

    private static final Random RANDOM = new Random();

    public Move getRandomMove() {
        Move[] moves = Move.values();
        return moves[RANDOM.nextInt(moves.length)];
    }
}
