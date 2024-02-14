package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.usertype.UserType;
import org.springframework.stereotype.Service;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.BorrowedBookExceptionMessages;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;
import io.library.library_3.user.entity.User;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {
    private BorrowedBookRepo borrowedBookRepo;
    private LibrarianRepo librarianRepo;
    private StudentRepo studentRepo;
    private BookRepo bookRepo;

    public BorrowedBookServiceImpl(BorrowedBookRepo borrowedBookRepo, LibrarianRepo librarianRepo,
            StudentRepo studentRepo, BookRepo bookRepo) {
        this.borrowedBookRepo = borrowedBookRepo;
        this.librarianRepo = librarianRepo;
        this.studentRepo = studentRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public void borrowBook(String refId, int userId, Date dateDue, @SuppressWarnings("rawtypes") UserType userType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrowBook'");
    }

    @Override
    public List<Book> getBooksBorrowed() {
        return borrowedBookRepo.findAllBooks();
    }

    @Override
    public List<User> getUsersBorrowingBook(String refId) {
        Optional<Book> opBook = bookRepo.findById(refId);
        if (opBook.isPresent()) {
            return borrowedBookRepo.findUsersBorrowingBook(opBook.get());
        } else {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND(refId));
        }
    }

    @Override
    public List<Book> getBooksBorrowedByUser(int userId, @SuppressWarnings("rawtypes") UserType userType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksBorrowedByUser'");
    }

    @Override
    public BorrowedBook updateBorrowedBookDate(BorrowedBook borrowedBook,
            @SuppressWarnings("rawtypes") UserType userType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBorrowedBookDate'");
    }

    @Override
    public void returnBook(int id, @SuppressWarnings("rawtypes") UserType userType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

}
