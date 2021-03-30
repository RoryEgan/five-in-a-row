package com.genesys.game.controller;

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
import java.util.Optional;

@Slf4j
@RestController
public class GameController {

    @Autowired
    private GameExecutionService executionService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayer(@RequestBody Player player) {
        executionService.addPlayer(player);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Game> makeMove(@RequestBody Move move) {
        return new ResponseEntity<>(executionService.handleMove(move), HttpStatus.OK);
    }

    @PostMapping("/startGame")
    public ResponseEntity<Player> startGame(@RequestBody Player player) throws InterruptedException {

        // Checks if 2 players connected. If so, return first player to make first move
        Optional<Player> playerOne = executionService.canGameStart();
        if(playerOne.isPresent()) {
            return new ResponseEntity<>(playerOne.get(), HttpStatus.CREATED);
        }

        return waitForGameStart(player);
    }

    @GetMapping("/getGameState")
    public ResponseEntity<Game> getGameState(@RequestParam String playerId) throws InterruptedException {

        Optional<Game> game = executionService.isPlayersMove(playerId);
        if(game.isPresent()) {
            return new ResponseEntity<>(game.get(), HttpStatus.OK);
        }

        return keepPolling(playerId);
    }

    private ResponseEntity<Game> keepPolling(String playerId) throws InterruptedException {
        Thread.sleep(500);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/getGameState?playerId=" + playerId));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }

    private ResponseEntity<Player> waitForGameStart(Player player) throws InterruptedException {
        Thread.sleep(500);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/startGame"));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
}
