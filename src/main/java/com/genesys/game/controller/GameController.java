package com.genesys.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.game.model.GetState;
import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import com.genesys.game.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@RestController
public class GameController {

    @Autowired
    private GameService gameService;


    private final int minNumOfPlayers = 2;

    @Autowired
    private ObjectMapper mapper;

//    @PostMapping("/makeMove")
//    public DeferredResult<ResponseEntity> makeMove(@RequestBody Move move) {
//        DeferredResult<ResponseEntity> output = new DeferredResult<>();
//
//
//        ForkJoinPool.commonPool().submit(() -> {
//            log.info("Processing in separate thread");
//            try {
//                Thread.sleep(6000);
//            } catch (InterruptedException e) {
//                log.info("disconnected");
//            }
//            List<Move> currentMoves = game.getMoves();
//            List<Move> updatedMoves = currentMoves.add(move);
//            game.setMoves(updatedMoves);
//            output.setResult(ResponseEntity.ok(updatedMoves));
//        });
//
//        log.info("servlet thread freed");
//        return output;
//    }

    @PostMapping("/startGame")
    public ResponseEntity startGame(@RequestBody Player player) throws JsonProcessingException, InterruptedException {
        List<Player> players = gameService.getPlayers();

        if(players.size() >= minNumOfPlayers) {
            return new ResponseEntity(players.get(0).getId(), HttpStatus.CREATED);
        }

        return waitForGameStart(player);
    }

    @PostMapping("/players")
    public ResponseEntity<List<Player>> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(gameService.addPlayer(player));
    }

    @PostMapping("/makeMove")
    public ResponseEntity<List<Move>> makeMove(@RequestBody Move move) throws JsonProcessingException {

        log.info("Make move called with: " + mapper.writeValueAsString(move));

        return new ResponseEntity(gameService.handleMove(move), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getGameState")
    public ResponseEntity<List<Move>> getGameState(GetState input) throws InterruptedException, JsonProcessingException {
        log.info("Game state check called with: " + mapper.writeValueAsString(input));
        if(gameService.isPlayersMove(input.getPlayerId())) {
            return ResponseEntity.ok(gameService.getMoves());
        }

        return keepPolling(input);
    }

    private ResponseEntity<List<Move>> keepPolling(GetState input) throws InterruptedException, JsonProcessingException {
        log.info("Keep polling called with: " + mapper.writeValueAsString(input));
        Thread.sleep(3000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/getGameState?moveId=" + input.getMoveId() + "&playerId=" + input.getPlayerId()));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    private ResponseEntity waitForGameStart(Player player) throws InterruptedException, JsonProcessingException {
        log.info("Waiting for game to start with: " + mapper.writeValueAsString(player));
        Thread.sleep(3000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/startGame"));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
}
