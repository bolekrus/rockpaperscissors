package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.enums.Move;
import com.example.rockpaperscissors.enums.Result;

public interface GameService {

    Result getResult(Move playerMove, Move computerMove);

    Move getRandomMove();
}
