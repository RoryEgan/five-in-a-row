package com.genesys.game.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class Game {

    private List<Player> players = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();
    private Board board;

}
