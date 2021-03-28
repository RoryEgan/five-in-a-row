package com.genesys.game.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GetState {

    private String moveId;
    private String playerId;

}
