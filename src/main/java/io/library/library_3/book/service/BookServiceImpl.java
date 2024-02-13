package io.library.library_3.book.service;

import org.springframework.stereotype.Service;

import io.library.library_3.book.entity.Book;
import io.library.library_3.enums.BookSearchType;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public void createBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBook'");
    }

    @Override
    public void getBooks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooks'");
    }

    @Override
    public void getBooksByTitle(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksByTitle'");
    }

    @Override
    public void getBooks(String[] arr, BookSearchType bookSearchType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooks'");
    }

    @Override
    public void getBook(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBook'");
    }

    @Override
    public void getBookByRefId(String refId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookByRefId'");
    }

    @Override
    public void updateBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public void deleteBook(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }

}
