package io.library.library_3.error_handling.exceptions;

public class InvalidEnumException extends RuntimeException {
    private String message;

    public InvalidEnumException() {
        super();
    }

    public InvalidEnumException(String message) {
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
