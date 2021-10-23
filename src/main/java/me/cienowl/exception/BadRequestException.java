package me.cienowl.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(Exception e) {
        super(e);
    }
}
