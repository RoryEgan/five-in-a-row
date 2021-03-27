package com.genesys.game.repository;

import com.genesys.game.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "game", path = "game")
public interface GameRepository extends MongoRepository<Game, String> {


}
