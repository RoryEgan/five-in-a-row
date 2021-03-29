package com.genesys.game.service.impl;

import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import com.genesys.game.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private Game game;

    @Override
    public List<Player> getPlayers() {
        return game.getPlayers();
    }


    @Override
    public boolean isPlayersMove(String playerId) {
        List<Move> currentMoves = game.getMoves();
        return !currentMoves.isEmpty() && !currentMoves.get(currentMoves.size() - 1).getPlayerId().equals(playerId);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public List<Player> addPlayer(Player player) {
        List<Player> players = game.getPlayers();
        if(players.size() == 0) {
            player.setSymbol('X');
        } else {
            player.setSymbol('O');
        }
        players.add(player);
        game.setPlayers(players);
        return players;
    }
}
