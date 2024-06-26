package com.yolo.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class PlayGameInputDto {
    //Validation added to accept numbers from 1 to 99. If we accept 100 also later the win calculation returns Infinity
    @Min(value = 1, message = "Number should be equal or greater than 1")
    @Max(value = 99, message = "Number should be less than 100")
    private final int number;

    //For bet its validated to accept numbers from 1 to 100
    @Min(value = 1, message = "Bet should be equal or greater than 1")
    @Max(value = 100, message = "Bet should be equal or less than 100")
    private final double bet;

    @JsonCreator
    public PlayGameInputDto(@JsonProperty(value = "number") int number, @JsonProperty(value = "bet") double bet) {
        this.number = number;
        this.bet = bet;
    }
}
