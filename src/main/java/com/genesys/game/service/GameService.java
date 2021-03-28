package com.genesys.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameService {

    @Autowired
    private Game game;

    @Autowired
    private ObjectMapper mapper;

    public Game startGame() {
        return game;
    }

    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    public List<Move> getMoves() {
        return game.getMoves();
    }

    public boolean isPlayersMove(String playerId) throws JsonProcessingException {
        List<Move> currentMoves = game.getMoves();
        log.info("Current moves: " + mapper.writeValueAsString(currentMoves));
        if (!currentMoves.isEmpty() && !currentMoves.get(currentMoves.size() - 1).getPlayerId().equals(playerId)) {
           return true;
        }
        return false;
    }

    public List<Player> addPlayer(Player player) {
        List<Player> players = game.getPlayers();
        players.add(player);
        game.setPlayers(players);
        return players;
    }

    public List<Move> handleMove(Move move) throws JsonProcessingException {
       List<Move> currentMoves = game.getMoves();
       currentMoves.add(move);
       game.setMoves(currentMoves);
       log.info("Handling move: " + mapper.writeValueAsString(move));
       return currentMoves;
   }
}
