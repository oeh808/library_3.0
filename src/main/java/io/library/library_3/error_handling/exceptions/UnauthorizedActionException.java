package io.library.library_3.error_handling.exceptions;

public class UnauthorizedActionException extends RuntimeException {
    private String message;

    public UnauthorizedActionException() {
        super();
    }

    public UnauthorizedActionException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}