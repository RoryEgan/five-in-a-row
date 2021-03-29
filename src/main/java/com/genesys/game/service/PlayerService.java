package com.genesys.game.service;

import com.genesys.game.model.Game;
import com.genesys.game.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getPlayers();

    boolean isPlayersMove(String playerId);

    void setGame(Game game);

    List<Player> addPlayer(Player player);
}
