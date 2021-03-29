package com.genesys.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;

public interface GameExecutionService {
    //Game executeGameFunctionality(String playerId);
    boolean isPlayersMove(String playerId);
    Game getGame();
    boolean canGameStart();
    String getPlayerOneId();
    void addPlayer(Player player);
    Game handleMove(Move move) throws JsonProcessingException;
}
