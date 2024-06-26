package com.yolo.service.controller;

import com.yolo.service.dto.PlayGameInputDto;
import com.yolo.service.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/play")
    public ResponseEntity<Double> play(@Valid @RequestBody PlayGameInputDto playGameInputDto){
        return ResponseEntity.ok(gameService.playGame(playGameInputDto));
    }
}
