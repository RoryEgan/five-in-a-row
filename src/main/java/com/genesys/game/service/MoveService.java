package com.genesys.game.service;

import com.genesys.game.model.Game;
import com.genesys.game.model.Move;

public interface MoveService {
    Game handleMove(Move move);
    void setGame(Game game);
}
