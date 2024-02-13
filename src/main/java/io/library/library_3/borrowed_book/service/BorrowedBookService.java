package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.user.entity.User;

public interface BorrowedBookService {
    // Create
    public void borrowBook(BorrowedBook borrowedBook);

    // Read
    // No need to retrieve borrowed books with BorrowedBook type
    public List<Book> getBooksBorrowed();

    public List<User> getUsersBorrowingBook(Book book);

    // Update
    public BorrowedBook updateBorrowedBookDate(Date dateDue);

    // Delete
    public void returnBook(BorrowedBook borrowedBook);
}
