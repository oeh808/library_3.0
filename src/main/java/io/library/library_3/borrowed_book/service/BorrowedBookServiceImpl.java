package io.library.library_3.borrowed_book.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.auth.AuthExceptionMessages;
import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.BorrowedBookExceptionMessages;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.error_handling.exceptions.OutOfStockException;
import io.library.library_3.error_handling.exceptions.UnauthorizedActionException;
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
    public BorrowedBook borrowBook(String refId, int userId, Date dateDue, UserTypeCustom userType) {
        Optional<Book> opBook = bookRepo.findById(refId);
        User user;

        // Check if the book exists
        if (opBook.isEmpty()) {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND(refId));
        }

        // Check if the user exists and is allowed to borrow
        if (userType.equals(UserTypeCustom.STUDENT)) {
            Optional<Student> opStudent = studentRepo.findById(userId);
            if (opStudent.isEmpty()) {
                throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(userId));
            } else if (!opStudent.get().isRegistered()) {
                // Unregistered students cannot borrow
                throw new UnauthorizedActionException(StudentExceptionMessages.NOT_REGISTERED);
            } else {
                user = opStudent.get();
            }
        } else {
            Optional<Librarian> opLibrarian = librarianRepo.findById(userId);
            if (opLibrarian.isEmpty()) {
                throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(userId));
            } else {
                user = opLibrarian.get();
            }
        }

        Book book = opBook.get();

        if (borrowedBookRepo.findUserBorrowingBook(book, user).isPresent()) {
            // User has already borrowed this book
            throw new DuplicateEntityException(BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle()));
        }

        // Check if book is in stock
        if (book.getQuantity() < 1) {
            throw new OutOfStockException(BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle()));
        }

        // Decrement quantity of books available
        book.setQuantity(book.getQuantity() - 1);
        bookRepo.save(book);

        // Finally borrow book
        return borrowedBookRepo.save(new BorrowedBook(book, user, dateDue));
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
    public BorrowedBook updateBorrowedBookDate(int userId, BorrowedBook borrowedBook) {
        BorrowedBook bb = getBorrowedBook(borrowedBook.getId());
        if (userId != bb.getUser().getId()) {
            throw new UnauthorizedActionException(AuthExceptionMessages.ACCESS_DENIED);
        }

        borrowedBook.setBook(bb.getBook());
        borrowedBook.setUser(bb.getUser());

        return borrowedBookRepo.save(borrowedBook);
    }

    @Override
    public void returnBook(int userId, int id) {
        // Check that borrowed book exists (Exception handling in getBorrowedBook)
        BorrowedBook borrowedBook = getBorrowedBook(id);
        if (userId != borrowedBook.getUser().getId()) {
            throw new UnauthorizedActionException(AuthExceptionMessages.ACCESS_DENIED);
        }

        // Increment quantity of books in stock
        Book book = borrowedBook.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepo.save(book);

        // Finally delete borrowed book
        borrowedBookRepo.deleteById(id);
    }

}
