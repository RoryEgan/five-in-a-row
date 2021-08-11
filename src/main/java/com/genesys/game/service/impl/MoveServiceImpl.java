package com.genesys.game.service.impl;

import com.genesys.game.exception.ColumnFullException;
import com.genesys.game.exception.InvalidColumnException;
import com.genesys.game.model.*;
import com.genesys.game.repository.CompletedGameRepository;
import com.genesys.game.service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MoveServiceImpl implements MoveService {

    private int lastCol = -1;
    private int lastTop = -1;
    private Game game;

    @Override
    public Game handleMove(Move move) {
        Player playerWhoMadeMove = getPlayerFromMove(move);
        performMove(move, playerWhoMadeMove);

        List<Move> currentMoves = game.getMoves();
        currentMoves.add(move);
        game.setMoves(currentMoves);

        if(game.getPlayState().equals(PlayState.COMPLETED)) {
            Player winningPlayer = getLastPlayer();
            game.setWinningPlayer(winningPlayer);
        }

        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    private void performMove(Move move, Player player) {

        int columnNumber = Integer.parseInt(move.getColumnNumber()) - 1;
        if (columnNumber < 0 || columnNumber > 8) {
            throw new InvalidColumnException("Invalid move, this is not a valid column number");
        }
        char[][] grid = game.getBoard().getGrid();

        for(int h = 5; h >= 0; h--) {
            if(grid[h][columnNumber] == '.') {
                grid[lastTop = h][lastCol = columnNumber] = player.getSymbol();
                isWinningMove(grid);
                return;
            }
        }

        throw new ColumnFullException("Invalid move, this column is full");
    }

    private void isWinningMove(char[][] grid) {
        char sym = grid[lastTop][lastCol];
        String streak = String.format("%c%c%c%c%c", sym, sym, sym, sym, sym);

        if(contains(horizontal(grid), streak) ||
                contains(vertical(grid), streak) ||
                contains(slashDiagonal(grid), streak) ||
                contains(backslashDiagonal(grid), streak)) {
            game.setPlayState(PlayState.COMPLETED);
        }

    }

    private static boolean contains(String str, String substring) {
        return str.contains(substring);
    }


    private String horizontal(char[][] grid) {
        return new String(grid[lastTop]);
    }

    private String vertical(char[][] grid) {
        StringBuilder sb = new StringBuilder(6);

        for (int h = 0; h < 6; h++) {
            sb.append(grid[h][lastCol]);
        }

        return sb.toString();
    }

    private String slashDiagonal(char[][] grid) {
        StringBuilder sb = new StringBuilder(6);

        for (int h = 0; h < 6; h++) {
            int w = lastCol + lastTop - h;

            if (0 <= w && w < 9) {
                sb.append(grid[h][w]);
            }
        }

        return sb.toString();
    }

    private String backslashDiagonal(char[][] grid) {
        StringBuilder sb = new StringBuilder(6);
        for (int h = 0; h < 6; h++) {
            int w = lastCol - lastTop + h;

            if (0 <= w && w < 9) {
                sb.append(grid[h][w]);
            }
        }

        return sb.toString();
    }

    private Player getLastPlayer() {
        List<Move> movesToDate = game.getMoves();
        Move move = movesToDate.get(movesToDate.size() - 1);

        return getPlayerFromMove(move);
    }

    private Player getPlayerFromMove(Move move) {
        String playerId = move.getPlayerId();
        List<Player> players = game.getPlayers();

        return players.stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findAny()
                .orElse(null);
    }
}
