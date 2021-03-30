package com.genesys.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class Game {

    private List<Player> players = new ArrayList<>();

    @JsonIgnore
    private List<Move> moves = new ArrayList<>();

    private Player winningPlayer;

    private PlayState playState = PlayState.ONGOING;

    @Autowired
    @JsonManagedReference
    private Board board;

}
