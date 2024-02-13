package io.library.library_3.book;

public class BookExceptionMessages {
    public static String REFID_NOT_FOUND(String refId) {
        return ("A book with refId = " + refId + " does not exist.");
    }
}
