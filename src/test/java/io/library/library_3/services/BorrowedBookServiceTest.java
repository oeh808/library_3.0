package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.BorrowedBookExceptionMessages;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.borrowed_book.service.BorrowedBookServiceImpl;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.error_handling.exceptions.OutOfStockException;
import io.library.library_3.error_handling.exceptions.UnauthorizedActionException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.student.StudentExceptionMessages;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;
import io.library.library_3.user.entity.User;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BorrowedBookServiceTest {
    @TestConfiguration
    static class BorrowedBookServiceTestConfig {
        @Bean
        @Autowired
        BorrowedBookService borrowedBookService(BorrowedBookRepo borrowedBookRepo, LibrarianRepo librarianRepo,
                StudentRepo studentRepo, BookRepo bookRepo) {
            return new BorrowedBookServiceImpl(borrowedBookRepo, librarianRepo, studentRepo, bookRepo);
        }
    }

    @MockBean
    private BorrowedBookRepo borrowedBookRepo;

    @MockBean
    private LibrarianRepo librarianRepo;

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private BookRepo bookRepo;

    @Autowired
    private BorrowedBookService borrowedBookService;

    private static User user;
    private static Book book;
    private static Date date;
    private static BorrowedBook borrowedBook;

    private static List<Book> books;

    @BeforeAll
    public static void setUp() {
        user = new Student("Inmo Bob", "Arts", "Selly Oak");
        user.setId(100);
        book = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });

        date = Date.valueOf("2030-09-15");

        borrowedBook = new BorrowedBook(book, user, date);
        borrowedBook.setId(1);

        books = new ArrayList<Book>();
        books.add(book);
    }

    @BeforeEach
    public void setUpMocks() {
        book.setQuantity(5);
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.of(book));
        when(studentRepo.findById(user.getId())).thenReturn(Optional.of((Student) user));
    }

    @Test
    public void borrowBook_Success() {
        Student student = (Student) user;
        student.setRegistered(true);
        when(studentRepo.findById(user.getId())).thenReturn(Optional.of(student));

        when(borrowedBookRepo.findUserBorrowingBook(book, student)).thenReturn(Optional.empty());
        when(borrowedBookRepo.save(any(BorrowedBook.class))).thenReturn(borrowedBook);

        assertEquals(borrowedBook,
                borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT));

        verify(bookRepo, times(1)).save(any(Book.class));
        student.setRegistered(false);
    }

    @Test
    public void borrowBook_BookNonExistant() {
        when(bookRepo.findById("Blah")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.borrowBook("Blah", user.getId(), date, UserTypeCustom.STUDENT);
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.REFID_NOT_FOUND("Blah")));

    }

    @Test
    public void borrowBook_UserNonExistant() {
        when(studentRepo.findById(user.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.borrowBook(book.getRefId(), user.getId() - 1, date, UserTypeCustom.STUDENT);
                });

        assertTrue(ex.getMessage().contains(StudentExceptionMessages.ID_NOT_FOUND(user.getId() - 1)));

        when(librarianRepo.findById(user.getId() - 1)).thenReturn(Optional.empty());

        ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.borrowBook(book.getRefId(), user.getId() - 1, date, UserTypeCustom.LIBRARIAN);
                });

        assertTrue(ex.getMessage().contains(LibrarianExceptionMessages.ID_NOT_FOUND(user.getId() - 1)));

    }

    @Test
    public void borrowBook_ActionNotAllowed() {
        UnauthorizedActionException ex = assertThrows(UnauthorizedActionException.class,
                () -> {
                    borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);
                });

        assertTrue(ex.getMessage().contains(StudentExceptionMessages.NOT_REGISTERED));

    }

    @Test
    public void borrowBook_BookAlreadyBorrowedByUser() {
        Student student = (Student) user;
        student.setRegistered(true);
        when(studentRepo.findById(user.getId())).thenReturn(Optional.of(student));

        when(borrowedBookRepo.findUserBorrowingBook(book, student)).thenReturn(Optional.of(student));

        DuplicateEntityException ex = assertThrows(DuplicateEntityException.class,
                () -> {
                    borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);
                });
        assertTrue(ex.getMessage().contains(BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle())));
        student.setRegistered(false);
    }

    @Test
    public void borrowBook_BookOutOfStock() {
        Student student = (Student) user;
        student.setRegistered(true);
        book.setQuantity(0);
        when(studentRepo.findById(user.getId())).thenReturn(Optional.of(student));

        when(borrowedBookRepo.findUserBorrowingBook(book, student)).thenReturn(Optional.empty());

        OutOfStockException ex = assertThrows(OutOfStockException.class,
                () -> {
                    borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);
                });

        assertTrue(ex.getMessage().contains(BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle())));
        student.setRegistered(false);
    }

    @Test
    public void getBooksBorrowed_Successful() {
        when(borrowedBookRepo.findAllBooks()).thenReturn(books);

        assertEquals(books, borrowedBookService.getBooksBorrowed());
    }

    @Test
    public void getUsersBorrowingBook_BookExistant() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(borrowedBookRepo.findUsersBorrowingBook(book)).thenReturn(users);

        assertEquals(users, borrowedBookService.getUsersBorrowingBook(book.getRefId()));

    }

    @Test
    public void getUsersBorrowingBook_BookNonExistant() {
        when(bookRepo.findById("Blah")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.getUsersBorrowingBook("Blah");
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.REFID_NOT_FOUND("Blah")));
    }

    @Test
    public void getBooksBorrowedByUser_UserExistant() {
        when(borrowedBookRepo.findBooksBorrowedByUser(any(Student.class))).thenReturn(books);

        assertEquals(books, borrowedBookService.getBooksBorrowedByUser(user.getId(), UserTypeCustom.STUDENT));
    }

    @Test
    public void getBooksBorrowedByUser_StudentNonExistant() {
        when(studentRepo.findById(user.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.getBooksBorrowedByUser(user.getId() - 1, UserTypeCustom.STUDENT);
                });

        assertTrue(ex.getMessage().contains(StudentExceptionMessages.ID_NOT_FOUND(user.getId() - 1)));
    }

    @Test
    public void getBooksBorrowedByUser_LibrarianNonExistant() {
        when(studentRepo.findById(user.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.getBooksBorrowedByUser(user.getId() - 1, UserTypeCustom.LIBRARIAN);
                });

        assertTrue(ex.getMessage().contains(LibrarianExceptionMessages.ID_NOT_FOUND(user.getId() - 1)));
    }

    @Test
    public void updateBorrowedBookDate_Existant() {
        BorrowedBook someBorrowedBook = new BorrowedBook();
        someBorrowedBook.setDateDue(date);
        someBorrowedBook.setId(borrowedBook.getId());

        BorrowedBook expectedBB = new BorrowedBook(book, user, date);
        expectedBB.setId(borrowedBook.getId());

        when(borrowedBookRepo.findById(borrowedBook.getId())).thenReturn(Optional.of(borrowedBook));
        when(borrowedBookRepo.save(any(BorrowedBook.class))).thenReturn(expectedBB);

        assertEquals(expectedBB, borrowedBookService.updateBorrowedBookDate(someBorrowedBook));
    }

    @Test
    public void updateBorrowedBookDate_NonExistant() {
        BorrowedBook someBorrowedBook = new BorrowedBook();
        someBorrowedBook.setDateDue(date);
        someBorrowedBook.setId(borrowedBook.getId() - 1);

        when(borrowedBookRepo.findById(borrowedBook.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.updateBorrowedBookDate(someBorrowedBook);
                });

        assertTrue(ex.getMessage().contains(BorrowedBookExceptionMessages.ID_NOT_FOUND(someBorrowedBook.getId())));
    }

    @Test
    public void returnBook_Existant() {
        when(borrowedBookRepo.findById(borrowedBook.getId())).thenReturn(Optional.of(borrowedBook));

        borrowedBookService.returnBook(borrowedBook.getId());

        verify(bookRepo, times(1)).save(any(Book.class));
        verify(borrowedBookRepo, times(1)).deleteById(borrowedBook.getId());
    }

    @Test
    public void returnBook_NonExistant() {
        when(borrowedBookRepo.findById(borrowedBook.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    borrowedBookService.returnBook(borrowedBook.getId() - 1);
                });

        assertTrue(ex.getMessage().contains(BorrowedBookExceptionMessages.ID_NOT_FOUND(borrowedBook.getId() - 1)));
    }
}
