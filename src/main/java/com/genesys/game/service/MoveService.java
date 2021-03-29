package com.genesys.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;

public interface MoveService {
    Game handleMove(Move move) throws JsonProcessingException;
    void setGame(Game game);
}
