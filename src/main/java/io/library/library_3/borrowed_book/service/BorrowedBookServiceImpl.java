package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.user.entity.User;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    @Override
    public void borrowBook(String refId, int userId, Date dateDue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrowBook'");
    }

    @Override
    public List<Book> getBooksBorrowed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksBorrowed'");
    }

    @Override
    public List<User> getUsersBorrowingBook(String refId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsersBorrowingBook'");
    }

    @Override
    public List<Book> getBooksBorrowedByUser(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksBorrowedByUser'");
    }

    @Override
    public BorrowedBook updateBorrowedBookDate(BorrowedBook borrowedBook) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBorrowedBookDate'");
    }

    @Override
    public void returnBook(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

}
