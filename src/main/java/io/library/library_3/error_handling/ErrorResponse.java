package io.library.library_3.error_handling;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        super();
        this.message = "ERROR: " + message;
    }

    // Getters and Setters
    // ________________________________________
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
