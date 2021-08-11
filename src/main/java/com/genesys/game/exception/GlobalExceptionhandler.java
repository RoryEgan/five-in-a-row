package com.genesys.game.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handleColumnFullException(ColumnFullException exception) {
        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleInvalidColumnException(InvalidColumnException exception) {
        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
