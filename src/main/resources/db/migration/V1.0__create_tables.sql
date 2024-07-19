CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR (50) UNIQUE NOT NULL,
    password_hash VARCHAR (100) NOT NULL
);
CREATE TABLE IF NOT EXISTS game_stats (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    games_win INT NOT NULL DEFAULT 0,
    games_lose INT NOT NULL DEFAULT 0,
    games_tied INT NOT NULL DEFAULT 0,
    total_games INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);