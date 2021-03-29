package com.genesys.game.controller;

import com.genesys.game.model.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void itShouldSendMessageToTheServer() {
        Move move = new Move();
        move.setColumnNumber("1");
        move.setPlayerId("1");
        List<Move> response = restTemplate.postForObject(url("/makeMove"), move, List.class);

        Assertions.assertEquals(1, response.size());
    }

    private String url(String path) {
        return String.format("http://localhost:%d/%s", port, path);
    }

    @Test
    public void itShouldPollTheServerUntilItGetsTheResponse() {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10000);
                Move move = new Move();
                move.setColumnNumber("1");
                move.setPlayerId("1");
                System.out.println("Sending Message: " + move);
                restTemplate.postForObject(url("/makeMove"), move, List.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        List<Move> response = restTemplate.getForObject(url("/getPlayState?moveId=1&playerId=1"), List.class);

        System.out.println("Response: " + response);

        assertEquals(1, response.size());
    }
}