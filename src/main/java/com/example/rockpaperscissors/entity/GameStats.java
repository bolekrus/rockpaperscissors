package com.example.rockpaperscissors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "game_stats")
@Data
@EqualsAndHashCode(callSuper = true)
public class GameStats extends AbstractEntity<Long> {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int gamesWin;
    private int gamesLose;
    private int gamesTied;
    private int totalGames;

}
