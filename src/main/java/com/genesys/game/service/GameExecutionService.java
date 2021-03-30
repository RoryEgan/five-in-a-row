package com.genesys.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;

import java.util.Optional;

public interface GameExecutionService {
    Optional<Game> isPlayersMove(String playerId);
    Optional<Player> canGameStart();
    void addPlayer(Player player);
    Game handleMove(Move move) throws JsonProcessingException;
}
