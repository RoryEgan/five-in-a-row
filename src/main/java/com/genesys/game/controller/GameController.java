package com.genesys.game.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GameController {

    @PostMapping("/makeMove")
    public ResponseEntity makeMove(@RequestParam String message) {
        log.info(message);
        return ResponseEntity.ok(message);
    }
}
