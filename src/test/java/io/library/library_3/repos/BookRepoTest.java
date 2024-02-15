package io.library.library_3.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;

@DataJpaTest
public class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;

    private static Book book1;
    private static Book book2;

    @BeforeAll
    public static void setUp() {
        book1 = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });
        book2 = new Book("Da Book of War", new String[] { "Some Guy" }, 125, 15, new String[] { "COMEDY" });
    }

    @BeforeEach
    public void setUpForEach() {
        bookRepo.save(book1);
        bookRepo.save(book2);
    }

    @AfterEach
    public void tearDownForEach() {
        bookRepo.deleteAll();
    }

    @Test
    public void findByTitle_Exact() {
        List<Book> res = bookRepo.findByTitleContainingIgnoreCase("War and Peace");

        assertTrue(res.size() == 1);
        assertEquals(res.get(0).getTitle(), book1.getTitle());
    }

    @Test
    public void findByTitle_IgnoresCase() {
        List<Book> res = bookRepo.findByTitleContainingIgnoreCase("wAR AND pEACE");

        assertTrue(res.size() == 1);
        assertEquals(res.get(0).getTitle(), book1.getTitle());
    }

    @Test
    public void findByTitle_Partial() {
        List<Book> res = bookRepo.findByTitleContainingIgnoreCase("War");

        assertTrue(res.size() == 2);
        assertNotEquals(res.get(0), res.get(1));
    }

    @Test
    public void findByTitle_NoMatch() {
        List<Book> res = bookRepo.findByTitleContainingIgnoreCase("Blah");

        assertTrue(res.size() == 0);
    }
}
