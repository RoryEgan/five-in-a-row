package com.genesys.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.game.model.*;
import com.genesys.game.service.GameExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
public class GameController {

    @Autowired
    private GameExecutionService executionService;

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

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayer(@RequestBody Player player) {
        executionService.addPlayer(player);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Game> makeMove(@RequestBody Move move) throws JsonProcessingException {
        return new ResponseEntity<>(executionService.handleMove(move), HttpStatus.OK);
    }

    @PostMapping("/startGame")
    public ResponseEntity<String> startGame(@RequestBody Player player) throws InterruptedException {

        if(executionService.canGameStart()) {
            return new ResponseEntity<>(executionService.getPlayerOneId(), HttpStatus.CREATED);
        }

        return waitForGameStart(player);
    }

    @GetMapping("/getGameState")
    public ResponseEntity<Game> getGameState(@RequestParam String playerId) throws InterruptedException {

        if(executionService.isPlayersMove(playerId)) {
            return new ResponseEntity<>(executionService.getGame(), HttpStatus.OK);
        }

        return keepPolling(playerId);
    }

    private ResponseEntity<Game> keepPolling(String playerId) throws InterruptedException {
//        log.info("Keep polling called with: " + mapper.writeValueAsString(input));
        Thread.sleep(1000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/getGameState?playerId=" + playerId));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    private ResponseEntity<String> waitForGameStart(Player player) throws InterruptedException {
//        log.info("Waiting for game to start with: " + mapper.writeValueAsString(player));
        Thread.sleep(1000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/startGame"));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
}
