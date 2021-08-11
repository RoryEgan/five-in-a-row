package com.genesys.game.exception;

public class ColumnFullException extends RuntimeException {
    public ColumnFullException(String message) {
        super(message);
    }
}