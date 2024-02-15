package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.borrowed_book.service.BorrowedBookServiceImpl;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;
import io.library.library_3.user.entity.User;

@ExtendWith(SpringExtension.class)
public class BorrowedBookServiceTest {
    // TODO: Complete unit test

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
        book = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });

        date = Date.valueOf("2030-09-15");

        borrowedBook = new BorrowedBook(book, user, date);

        books = new ArrayList<Book>();
        books.add(book);
    }

    @Test
    public void borrowBook_Success() {
        // TODO: Complete test method
    }

    @Test
    public void borrowBook_BookNonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void borrowBook_UserNonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void borrowBook_ActionNotAllowed() {
        // TODO: Complete test method
    }

    @Test
    public void borrowBook_BookAlreadyBorrowedByUser() {
        // TODO: Complete test method
    }

    @Test
    public void borrowBook_BookOutOfStock() {
        // TODO: Complete test method
    }

    @Test
    public void getBooksBorrowed_Successful() {
        when(borrowedBookRepo.findAllBooks()).thenReturn(books);

        assertEquals(books, borrowedBookService.getBooksBorrowed());
    }

    @Test
    public void getUsersBorrowingBook_BookExistant() {
        // TODO: Complete test method
    }

    @Test
    public void getUsersBorrowingBook_BookNonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void getBooksBorrowedByUser_UserExistant() {
        // TODO: Complete test method
    }

    @Test
    public void getBooksBorrowedByUser_StudentNonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void getBooksBorrowedByUser_LibrarianNonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void updateBorrowedBookDate_Existant() {
        // TODO: Complete test method
    }

    @Test
    public void updateBorrowedBookDate_NonExistant() {
        // TODO: Complete test method
    }

    @Test
    public void returnBook_Existant() {
        // TODO: Complete test method
    }

    @Test
    public void returnBook_NonExistant() {
        // TODO: Complete test method
    }
}
