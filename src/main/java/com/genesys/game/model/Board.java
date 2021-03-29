package com.genesys.game.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@Getter
@Setter
public class Board {

    private static final int HEIGHT = 6;
    private static final int WIDTH = 9;
    private char[][] grid;

    @PostConstruct
    public void init() {
        grid = new char[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            Arrays.fill(grid[i] = new char[WIDTH], '.');
        }
    }
}
