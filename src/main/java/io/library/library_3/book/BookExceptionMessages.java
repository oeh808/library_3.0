package io.library.library_3.book;

public class BookExceptionMessages {
    public static String REFID_NOT_FOUND(String refId) {
        return ("A book with refId = " + refId + " does not exist.");
    }

    public static String BOOK_WITH_TITLE_EXISTS(String title) {
        return ("A book with title = (" + title + ") already exists.");
    }

    public static String INVALID_CATEGORY(String category) {
        return (category + " is not a valid category.");
    }
}
