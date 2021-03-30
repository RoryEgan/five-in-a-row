package it.com.genesys.game.controller;

import com.genesys.game.model.Move;
import com.genesys.game.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GameControllerTest {

    @LocalServerPort
    private int port;

    @Bean
    @Primary
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS, TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testAddPlayersAndStartGame() {
        Player player1 = new Player();
        player1.setName("Test");
        player1.setPlayerId("hdhd");
        restTemplate.postForObject(url("/players"), player1, String.class);

        Player player2 = new Player();
        player1.setName("Test2");
        player1.setPlayerId("khka");
        restTemplate.postForObject(url("/players"), player2, String.class);

        Player response = restTemplate.postForObject(url("/startGame"), player1, Player.class);

        assertEquals("hdhd", response.getPlayerId());

    }

    @Test
    public void testMakeMove() {
        Player player1 = new Player();
        player1.setName("Test");
        player1.setPlayerId("hdhd");
        restTemplate.postForObject(url("/players"), player1, String.class);

        Player player2 = new Player();
        player1.setName("Test2");
        player1.setPlayerId("khka");
        restTemplate.postForObject(url("/players"), player2, String.class);

        restTemplate.postForObject(url("/startGame"), player1, Player.class);

        Move move = new Move();
        move.setPlayerId("hdhd");
        move.setColumnNumber("1");

        ResponseEntity<String> gameResponse = restTemplate.postForEntity(url("/makeMove"), move, String.class);
        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

    }

    @Test
    public void testCheckGameState() {
        Player player1 = new Player();
        player1.setName("Test");
        player1.setPlayerId("hdhd");
        restTemplate.postForObject(url("/players"), player1, String.class);

        Player player2 = new Player();
        player1.setName("Test2");
        player1.setPlayerId("khka");
        restTemplate.postForObject(url("/players"), player2, String.class);

        restTemplate.postForObject(url("/startGame"), player1, Player.class);

        Move move = new Move();
        move.setPlayerId("hdhd");
        move.setColumnNumber("1");

        restTemplate.postForEntity(url("/makeMove"), move, String.class);

        ResponseEntity<String> gameResponse = restTemplate.getForEntity(url("/getGameState?playerId=khka"), String.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

    }

    private String url(String path) {
        return String.format("http://localhost:%d/%s", port, path);
    }
}