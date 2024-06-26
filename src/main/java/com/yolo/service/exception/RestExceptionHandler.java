package com.yolo.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    /** Controller advise used as a global exception handler to catch exceptions and
    Construct is as Form of ApiError and send as the response when
    Exception occurred */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgsNotValidExceptions(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> sb.append("In field ")
                .append(((FieldError) error).getField()).append(", ").append(error.getDefaultMessage()).append(".  "));
        log.error(String.format("Method arguments are not valid. %s", sb.toString()));
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), sb.toString());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
