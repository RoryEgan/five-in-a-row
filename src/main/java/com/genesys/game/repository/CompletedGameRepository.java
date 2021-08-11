package com.genesys.game.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.genesys.game.model.CompletedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompletedGameRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public CompletedGameRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public CompletedGame saveGame(CompletedGame completedGame) {
        dynamoDBMapper.save(completedGame);
        return completedGame;
    }
}
