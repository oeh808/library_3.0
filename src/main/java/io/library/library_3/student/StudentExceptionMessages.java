package io.library.library_3.student;

public class StudentExceptionMessages {
    public static String NOT_REGISTERED = "You cannot go through with this action until your registeration is approved.";

    public static String ID_NOT_FOUND(int id) {
        return ("A student with id = " + id + " does not exist.");
    }
}
