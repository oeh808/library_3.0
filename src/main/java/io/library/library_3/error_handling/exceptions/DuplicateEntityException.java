package io.library.library_3.error_handling.exceptions;

public class DuplicateEntityException extends RuntimeException {
    private String message;

    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(String message) {
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
