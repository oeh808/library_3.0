package io.library.library_3.book.service;

import io.library.library_3.book.entity.Book;
import io.library.library_3.enums.BookSearchType;

public interface BookService {
    // Create
    public void createBook(Book book);

    // Read
    public void getBooks();

    public void getBooksByTitle(String title);

    public void getBooks(String[] arr, BookSearchType bookSearchType);

    public void getBook(int id);

    public void getBookByRefId(String refId);

    // Update
    public void updateBook(Book book);

    // Delete
    public void deleteBook(int id);
}
