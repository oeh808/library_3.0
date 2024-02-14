package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;

import org.hibernate.usertype.UserType;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.user.entity.User;

public interface BorrowedBookService {
    // Create
    public void borrowBook(String refId, int userId, Date dateDue, @SuppressWarnings("rawtypes") UserType userType);

    // Read
    // No need to retrieve borrowed books with BorrowedBook type
    public List<Book> getBooksBorrowed();

    public List<User> getUsersBorrowingBook(String refId);

    public List<Book> getBooksBorrowedByUser(int userId, @SuppressWarnings("rawtypes") UserType userType);

    // Update
    public BorrowedBook updateBorrowedBookDate(BorrowedBook borrowedBook,
            @SuppressWarnings("rawtypes") UserType userType);

    // Delete
    public void returnBook(int id, @SuppressWarnings("rawtypes") UserType userType);
}
