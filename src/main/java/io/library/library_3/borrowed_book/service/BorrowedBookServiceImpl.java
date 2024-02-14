package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.BorrowedBookExceptionMessages;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.student.StudentExceptionMessages;
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
    public void borrowBook(String refId, int userId, Date dateDue, UserTypeCustom userType) {
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
    public List<Book> getBooksBorrowedByUser(int userId, UserTypeCustom userType) {
        if (userType.equals(UserTypeCustom.STUDENT)) {
            Optional<Student> opStudent = studentRepo.findById(userId);
            if (opStudent.isPresent()) {
                return borrowedBookRepo.findBooksBorrowedByUser(opStudent.get());
            } else {
                throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(userId));
            }
        } else {
            Optional<Librarian> opLibrarian = librarianRepo.findById(userId);
            if (opLibrarian.isPresent()) {
                return borrowedBookRepo.findBooksBorrowedByUser(opLibrarian.get());
            } else {
                throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(userId));
            }
        }
    }

    public BorrowedBook getBorrowedBook(int id) {
        Optional<BorrowedBook> opBorrowedBook = borrowedBookRepo.findById(id);
        if (opBorrowedBook.isPresent()) {
            return opBorrowedBook.get();
        } else {
            throw new EntityNotFoundException(BorrowedBookExceptionMessages.ID_NOT_FOUND(id));
        }
    }

    @Override
    public BorrowedBook updateBorrowedBookDate(BorrowedBook borrowedBook,
            UserTypeCustom userType) {
        getBorrowedBook(borrowedBook.getId());

        User user = borrowedBook.getUser();
        if (userType.equals(UserTypeCustom.STUDENT)) {
            if (studentRepo.findById(user.getId()).isEmpty())
                throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(user.getId()));
        } else {
            if (librarianRepo.findById(user.getId()).isEmpty())
                throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(user.getId()));
        }

        return borrowedBookRepo.save(borrowedBook);
    }

    @Override
    public void returnBook(int id, UserTypeCustom userType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBook'");
    }

}
