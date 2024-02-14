package io.library.library_3.borrowed_book;

public class BorrowedBookExceptionMessages {
    public static String BOOK_ALREADY_BORROWED(String title) {
        return ("You have already borrowed (" + title + ").");
    }

    public static String ID_NOT_FOUND(int id) {
        return ("A borrowed book with id = " + id + " does not exist.");
    }

    public static String BOOK_OUT_OF_STOCK(String title) {
        return ("(" + title + ") is out of stock, please try again at a later date.");
    }
}
