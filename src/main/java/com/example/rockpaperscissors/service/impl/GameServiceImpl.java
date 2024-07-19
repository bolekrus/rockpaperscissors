package com.example.rockpaperscissors.service.impl;

import com.example.rockpaperscissors.enums.Move;
import com.example.rockpaperscissors.enums.Result;
import com.example.rockpaperscissors.service.GameService;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private static final Map<Move, Map<Move, Result>> resultsMap = new EnumMap<>(Move.class);

    static {
        initializeResults();
    }

    private static void initializeResults() {
        resultsMap.put(Move.ROCK, createResultsMap(Result.DRAW, Result.LOSE, Result.WIN));
        resultsMap.put(Move.PAPER, createResultsMap(Result.WIN, Result.DRAW, Result.LOSE));
        resultsMap.put(Move.SCISSORS, createResultsMap(Result.LOSE, Result.WIN, Result.DRAW));
    }

    private static Map<Move, Result> createResultsMap(Result vsRock, Result vsPaper, Result vsScissors) {
        Map<Move, Result> map = new EnumMap<>(Move.class);
        map.put(Move.ROCK, vsRock);
        map.put(Move.PAPER, vsPaper);
        map.put(Move.SCISSORS, vsScissors);
        return map;
    }

    @Override
    public Result getResult(Move playerMove, Move computerMove) {
        return resultsMap.get(playerMove).get(computerMove);
    }
}
