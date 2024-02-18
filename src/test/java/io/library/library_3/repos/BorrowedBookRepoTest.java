package io.library.library_3.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.user.entity.User;

@DataJpaTest
public class BorrowedBookRepoTest {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private LibrarianRepo librarianRepo;

    @Autowired
    private BorrowedBookRepo borrowedBookRepo;

    private static Book book1;
    private Book savedBook1;
    private static Book book2;
    private Book savedBook2;

    private static User user1;
    private User savedUser1;
    private static User user2;
    private User savedUser2;

    private static Date date;

    private BorrowedBook borrowedBook1;
    private BorrowedBook borrowedBook2;

    @BeforeAll
    public static void setUp() {
        book1 = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });
        book2 = new Book("Da Book of War", new String[] { "Some Guy" }, 125, 13, new String[] { "COMEDY" });

        user1 = new Librarian("Craig Robinson", 3);
        user2 = new Librarian("Mark Green", 15);

        date = new Date(System.currentTimeMillis());
    }

    @BeforeEach
    public void setUpForEach() {
        savedBook1 = bookRepo.save(book1);
        savedBook2 = bookRepo.save(book2);

        savedUser1 = librarianRepo.save((Librarian) user1);
        savedUser2 = librarianRepo.save((Librarian) user2);

        borrowedBook1 = new BorrowedBook(savedBook1, savedUser1, date);
        borrowedBook2 = new BorrowedBook(savedBook1, savedUser2, date);

        borrowedBookRepo.save(borrowedBook1);
        borrowedBookRepo.save(borrowedBook2);
    }

    @AfterEach
    public void tearDownForEach() {
        borrowedBookRepo.deleteAll();
        bookRepo.deleteAll();
        librarianRepo.deleteAll();

    }

    @Test
    public void findAllBooks_ReturnsSingletonListOfBooks() {
        List<Book> res = borrowedBookRepo.findAllBooks();

        assertTrue(res.size() == 1);
        assertTrue(res.contains(savedBook1));
    }

    @Test
    public void findUsersBorrowingBook_ReturnsPopulatedList() {
        List<User> res = borrowedBookRepo.findUsersBorrowingBook(savedBook1);

        assertTrue(res.contains(savedUser1));
        assertTrue(res.contains(savedUser2));
    }

    @Test
    public void findUsersBorrowingBook_ReturnsEmptyList() {
        List<User> res = borrowedBookRepo.findUsersBorrowingBook(savedBook2);

        assertTrue(res.isEmpty());
    }

    @Test
    public void findBooksBorrowedByUser_ReturnsSingletonList() {
        List<Book> res1 = borrowedBookRepo.findBooksBorrowedByUser(savedUser1);
        List<Book> res2 = borrowedBookRepo.findBooksBorrowedByUser(savedUser2);

        assertTrue(res1.size() == 1);
        assertEquals(res1, res2);
    }

    @Test
    public void findUserBorrowingBook_ReturnsPresentOptional() {
        Optional<User> opUser = borrowedBookRepo.findUserBorrowingBook(savedBook1, savedUser1);

        assertTrue(opUser.isPresent());
        assertEquals(savedUser1, opUser.get());
    }

    @Test
    public void findUserBorrowingBook_ReturnsEmptyOptional() {
        Optional<User> opUser = borrowedBookRepo.findUserBorrowingBook(savedBook2, savedUser1);

        assertTrue(opUser.isEmpty());
    }
}
