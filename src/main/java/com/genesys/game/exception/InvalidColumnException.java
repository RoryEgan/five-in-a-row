package com.genesys.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidColumnException extends RuntimeException {
    public InvalidColumnException(String message) {
        super(message);
    }
}
