package com.yolo.service;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.yolo.service.dto.PlayGameInputDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerITest {

    @LocalServerPort
    private int port;

    private static final String GAME_PLAY_RESOURCE = "/api/v1/game/play";

    private PlayGameInputDto playGameInputDto;
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        playGameInputDto = new PlayGameInputDto(50,40.5);
    }

    @Test
    public void testGamePlayWithValidNumberAndBet(){
        RestAssured.given().contentType(ContentType.JSON).body(playGameInputDto)
                .post(GAME_PLAY_RESOURCE).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testGamePlayWithInValidNumberAndValidBet(){

        PlayGameInputDto playGameInputDtoOne = new PlayGameInputDto(120,40.5);
        RestAssured.given().contentType(ContentType.JSON).body(playGameInputDtoOne)
                .post(GAME_PLAY_RESOURCE).then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testGamePlayWithInValidNumberAndInValidBet(){

        PlayGameInputDto playGameInputDtTwo = new PlayGameInputDto(150,0.02);
        RestAssured.given().contentType(ContentType.JSON).body(playGameInputDtTwo)
                .post(GAME_PLAY_RESOURCE).then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
