package com.genesys.game.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import com.genesys.game.service.PlayerService;
import com.genesys.game.service.MoveService;
import com.genesys.game.service.GameExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GameExecutionServiceImpl implements GameExecutionService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private Game game;

    private final int NUM_PLAYERS_REQUIRED = 2;

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
    public Optional<Game> isPlayersMove(String playerId) {
        if(playerService.isPlayersMove(playerId)) {
            return Optional.of(game);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Player> canGameStart() {
        List<Player> players = playerService.getPlayers();
        if(players.size() >= NUM_PLAYERS_REQUIRED) {
            return Optional.of(players.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void addPlayer(Player player) {
        playerService.addPlayer(player);
    }

    @Override
    public Game handleMove(Move move) {
        return moveService.handleMove(move);
    }
}
