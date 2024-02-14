package io.library.library_3.error_handling.exceptions;

public class OutOfStockException extends RuntimeException {
    private String message;

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
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
