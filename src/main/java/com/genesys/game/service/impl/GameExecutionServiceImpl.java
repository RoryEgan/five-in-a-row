package com.genesys.game.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import com.genesys.game.service.PlayerService;
import com.genesys.game.service.MoveService;
import com.genesys.game.service.GameExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GameExecutionServiceImpl implements GameExecutionService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private Game game;

    @PostConstruct
    public void init() {
        playerService.setGame(game);
        moveService.setGame(game);
    }


//    @Override
//    public Game executeGameFunctionality(String playerId) {
//        return playerService.get
//    }

    @Override
    public boolean isPlayersMove(String playerId) {
        return playerService.isPlayersMove(playerId);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean canGameStart() {
        List<Player> players = playerService.getPlayers();

        if(players.size() >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public String getPlayerOneId() {
        return game.getPlayers().get(0).getId();
    }

    @Override
    public void addPlayer(Player player) {
        playerService.addPlayer(player);
    }

    @Override
    public Game handleMove(Move move) throws JsonProcessingException {
        return moveService.handleMove(move);
    }
}
