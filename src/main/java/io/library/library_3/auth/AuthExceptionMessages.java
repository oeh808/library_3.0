package io.library.library_3.auth;

public class AuthExceptionMessages {
    public static String ACCESS_DENIED = "Access denied.";

    public static String USERNAME_ALREADY_EXISTS(String username) {
        return ("A user with username: (" + username + ") already exists.");
    }
}
