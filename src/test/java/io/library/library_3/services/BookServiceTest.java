package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.book.service.BookService;
import io.library.library_3.book.service.BookServiceImpl;
import io.library.library_3.enums.BookSearchType;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.search.LinearSearchService;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @TestConfiguration
    static class BookServiceTestConfig {
        @Bean
        @Autowired
        BookService bookService(BookRepo bookRepo, LinearSearchService searchService) {
            return new BookServiceImpl(bookRepo, new LinearSearchService());
        }
    }

    @MockBean
    private BookRepo bookRepo;

    @MockBean
    private LinearSearchService searchService;

    @Autowired
    private BookService bookService;

    private static Book book;

    @BeforeAll
    public static void setUp() {
        book = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });
    }

    @Test
    public void createBook_Successful() {
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.empty());
        when(bookRepo.save(book)).thenReturn(book);

        assertEquals(book, bookService.createBook(book));
    }

    @Test
    public void createBook_ThrowsDuplicateException() {
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.of(book));

        DuplicateEntityException ex = assertThrows(DuplicateEntityException.class,
                () -> {
                    bookService.createBook(book);
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.BOOK_WITH_TITLE_EXISTS(book.getTitle())));
    }

    @Test
    public void getBooks_Successful() {
        bookService.getBooks();

        verify(bookRepo, times(1)).findAll();
    }

    @Test
    public void getBooksByTitle_Successful() {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookRepo.findByTitleContainingIgnoreCase(book.getTitle())).thenReturn(books);

        assertEquals(books, bookService.getBooksByTitle(book.getTitle()));
    }

    @Test
    public void getBooks_ByAuthors_Successful() {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookRepo.findAll()).thenReturn(books);
        when(searchService.search(book.getAuthors(), book.getAuthors())).thenReturn(true);

        assertEquals(books, bookService.getBooks(book.getAuthors(), BookSearchType.AUTHORS));

    }

    @Test
    public void getBooks_ByCategories_Successful() {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookRepo.findAll()).thenReturn(books);
        when(searchService.search(book.getCategories(), book.getCategories())).thenReturn(true);

        assertEquals(books, bookService.getBooks(book.getCategories(), BookSearchType.CATEGORIES));
    }

    @Test
    public void getBook_Existant() {
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.getBook(book.getRefId()));
    }

    @Test
    public void getBook_NonExistant() {
        when(bookRepo.findById("Some ref")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    bookService.getBook("Some ref");
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.REFID_NOT_FOUND("Some ref")));
    }

    @Test
    public void updateBook_Existant() {
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.of(book));
        when(bookRepo.save(book)).thenReturn(book);

        assertEquals(book, bookService.updateBook(book));
    }

    @Test
    public void updateBook_NonExistant() {
        when(bookRepo.findById("Some ref")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    Book someBook = new Book();
                    someBook.setRefId("Some ref");
                    bookService.updateBook(someBook);
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.REFID_NOT_FOUND("Some ref")));
    }

    @Test
    public void deleteBook_Existant() {
        when(bookRepo.findById(book.getRefId())).thenReturn(Optional.of(book));

        bookService.deleteBook(book.getRefId());

        verify(bookRepo, times(1)).deleteById(book.getRefId());
    }

    @Test
    public void deleteBook_NonExistant() {
        when(bookRepo.findById("Some ref")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    bookService.deleteBook("Some ref");
                });

        assertTrue(ex.getMessage().contains(BookExceptionMessages.REFID_NOT_FOUND("Some ref")));
    }

}
