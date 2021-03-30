package com.genesys.game.service.impl;

import com.genesys.game.model.Game;
import com.genesys.game.model.Move;
import com.genesys.game.service.MoveService;
import com.genesys.game.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameExecutionServiceImplTest {

    @Mock
    MoveService moveService;

    @Mock
    PlayerService playerService;

    @Mock
    Game game;

    @InjectMocks
    GameExecutionServiceImpl executionService;

    @Test
    public void returnPlayerWhenIsPlayersMove() {
        String testPlayerId = "kgka";
        when(playerService.isPlayersMove(any(String.class))).thenReturn(true);

        Optional<Game> testGame = executionService.isPlayersMove(testPlayerId);

        assertNotNull(testGame);
    }

    @Test
    public void returnEmptyWhenIsNotPlayersMove() {
        String testPlayerId = "kgka";
        when(playerService.isPlayersMove(any(String.class))).thenReturn(false);

        Optional<Game> testGame = executionService.isPlayersMove(testPlayerId);

        assert(!testGame.isPresent());
    }

    @Test
    public void returnGameWhenPlayerMoves() {
        Game testGame = new Game();
        when(moveService.handleMove(any(Move.class))).thenReturn(testGame);

        Game gameReturned = executionService.handleMove(new Move());

        assertNotNull(gameReturned);
    }

}
