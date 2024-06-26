package com.yolo.service;

import com.yolo.service.dto.PlayGameInputDto;
import com.yolo.service.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
public class MultiThreadedCalculationTest {

    private static ExecutorService executorService;

    @Autowired
    private GameService gameService;

    @BeforeAll
    public static void setUp() {
        //Setting up a executor service which contain a thread pool of 24 threads,
        // which use to execute game plays parallel
        int numOfThreads = 24;
        executorService = Executors.newFixedThreadPool(numOfThreads);
    }

    @AfterAll
    public static void cleanUp() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Test
    public void testGamePlayCalculation() {

        double totalBetAmount = 0.0;
        Random random = new Random();
        int noOfRounds = 1000000;

        //setting up no of game plays with random user generated number and bet
        List<GamePlayTask> games = new ArrayList<>();
        for (int round = 0; round < noOfRounds; round++) {
            //int userGeneratedNum = random.nextInt((100 - 1) + 1) + 1;
            int userGeneratedNum = random.nextInt(99) + 1;
            double userGeneratedBet = Math.round((1 + (99 * random.nextDouble())) * 100.0) / 100.0;

            totalBetAmount += userGeneratedBet;
            GamePlayTask gamePlayTask = new GamePlayTask(userGeneratedNum, userGeneratedBet, gameService);
            games.add(gamePlayTask);
        }

        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < noOfRounds; i++) {
            futures.add(executorService.submit(games.get(i)));
        }

        double totalWinAmount = 0.0;
        for (Future<Double> future : futures) {
            try {
                totalWinAmount += future.get(); // sum up the win amount for each game play
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        }
        log.info("Total win amount " + totalWinAmount);
        log.info("Total bet amount " + totalBetAmount);

        double rtp = (totalWinAmount / totalBetAmount) * 100;

        log.info("Player RTP " + finalRtpRoundUp(rtp));
    }

    private double finalRtpRoundUp(double result) {
        BigDecimal num = new BigDecimal(Double.toString(result));
        num = num.setScale(2, RoundingMode.UP);
        return num.doubleValue();
    }
}

@Slf4j
class GamePlayTask implements Callable<Double> {

    private int number;
    private double bet;

    private GameService gameService;

    public GamePlayTask(int number, double bet, GameService gameService) {
        this.number = number;
        this.bet = bet;
        this.gameService = gameService;
    }

    @Override
    public Double call() {
        return gamePlay();
    }

    private Double gamePlay() {
        PlayGameInputDto playGameInputDto = new PlayGameInputDto(number, bet);
        return gameService.playGame(playGameInputDto);
    }
}


