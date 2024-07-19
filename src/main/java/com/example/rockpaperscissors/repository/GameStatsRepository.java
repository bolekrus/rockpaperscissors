package com.example.rockpaperscissors.repository;

import com.example.rockpaperscissors.entity.GameStats;
import com.example.rockpaperscissors.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {

    Optional<GameStats> findByUser(User user);
}
