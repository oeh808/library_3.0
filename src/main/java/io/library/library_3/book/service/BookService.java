package io.library.library_3.book.service;

import java.util.List;

import io.library.library_3.book.entity.Book;
import io.library.library_3.enums.BookSearchType;

public interface BookService {
    // Create
    public void createBook(Book book);

    // Read
    public List<Book> getBooks();

    public List<Book> getBooksByTitle(String title);

    public List<Book> getBooks(String[] arr, BookSearchType bookSearchType);

    public Book getBook(String id);

    // Update
    public Book updateBook(Book book);

    // Delete
    public void deleteBook(int id);
}
