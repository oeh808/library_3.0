package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.user.entity.User;

public interface BorrowedBookService {
    // Create
    public BorrowedBook borrowBook(String refId, int userId, Date dateDue, UserTypeCustom userType);

    // Read
    // No need to retrieve borrowed books with BorrowedBook type
    public List<Book> getBooksBorrowed();

    public List<User> getUsersBorrowingBook(String refId);

    public List<Book> getBooksBorrowedByUser(int userId, UserTypeCustom userType);

    // Update
    public BorrowedBook updateBorrowedBookDate(BorrowedBook borrowedBook,
            UserTypeCustom userType);

    // Delete
    public void returnBook(int id);
}
