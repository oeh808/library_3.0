package io.library.library_3.error_handling.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private String message;

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
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
