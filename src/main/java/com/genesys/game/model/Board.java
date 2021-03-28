package com.genesys.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {

    private static final int ROWS = 5;
    private static final int COLUMNS = 9;
    //private Cell[][] cells = new Cell[ROWS][COLUMNS];
    private List<List<Cell>> cells = new ArrayList<List<Cell>>(COLUMNS);
}
