package com.yolo.service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiError {

    private int status;
    private String message;

    public ApiError() {
    }

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
