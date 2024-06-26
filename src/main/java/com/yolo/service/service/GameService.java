package com.yolo.service.service;

import com.yolo.service.dto.PlayGameInputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class GameService {

    public Double playGame(PlayGameInputDto playGameInputDto) {

        Random rand = new Random();

        //Randomly generate a number from 1 to 100
        int serverGeneratedNum = rand.nextInt(100) + 1;

        if (playGameInputDto.getNumber() > serverGeneratedNum) {
            double v = playGameInputDto.getBet() * ((double) 99 / (100 - playGameInputDto.getNumber()));
            log.info("Server Gen Num {} ,user gen number {}, bet {}, win {}  ", serverGeneratedNum, playGameInputDto.getNumber(), playGameInputDto.getBet(),v);
            return v;
        }
        log.info("Server Gen Num {} ,user gen number {}, bet {} , win {} ", serverGeneratedNum, playGameInputDto.getNumber(), playGameInputDto.getBet(),0.0);

        return 0.0; // if the server generated number is higher than the user's number no win. Return 0 as the output
    }
}
