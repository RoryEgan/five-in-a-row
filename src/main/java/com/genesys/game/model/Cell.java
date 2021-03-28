package com.genesys.game.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    private Seed content;
    private int row;
    private int column;
}
