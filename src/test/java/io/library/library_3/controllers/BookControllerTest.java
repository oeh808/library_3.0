package io.library.library_3.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.controller.BookController;
import io.library.library_3.book.dtos.BookCreationDTO;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.mapper.BookMapper;
import io.library.library_3.book.service.BookService;
import io.library.library_3.enums.BookSearchType;
import io.library.library_3.enums.Category;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.error_handling.exceptions.InvalidEnumException;

@ActiveProfiles("test")
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    ObjectMapper mapper = new ObjectMapper();

    private static BookCreationDTO invalidCreationDTO;

    private static Book book;
    private static BookCreationDTO creationDTO;

    @BeforeAll
    public static void setUp() {
        invalidCreationDTO = new BookCreationDTO();
        invalidCreationDTO.setTitle("");
        invalidCreationDTO.setNumOfPages(-1);
        invalidCreationDTO.setQuantity(-1);

        book = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });
        creationDTO = toDTO(book);
    }

    @BeforeEach
    public void setUpMocks() {
        when(bookMapper.toBook(any(BookCreationDTO.class))).thenReturn(book);
    }

    @Test
    public void addBook_Valid() throws Exception {
        when(bookService.createBook(book)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void addBook_Duplicate() throws Exception {
        doAnswer((i) -> {
            throw new DuplicateEntityException(BookExceptionMessages.BOOK_WITH_TITLE_EXISTS(book.getTitle()));
        }).when(bookService).createBook(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void addBook_Invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .content(mapper.writeValueAsString(invalidCreationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getBooks() throws Exception {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookService.getBooks()).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getBooks_Empty() throws Exception {
        List<Book> books = new ArrayList<Book>();
        when(bookService.getBooks()).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBooksByTitle_Existant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookService.getBooksByTitle(book.getTitle())).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byTitle/" + book.getTitle()))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getBooksByTitle_NonExistant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        when(bookService.getBooksByTitle("Bla")).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byTitle/" + "Bla"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBooksByAuthors_Existant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookService.getBooks(book.getAuthors(), BookSearchType.AUTHORS)).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byAuthors")
                        .content(mapper.writeValueAsString(book.getAuthors()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getBooksByAuthors_NonExistant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        String[] fakeAuthors = new String[] { "Joe Shmoe" };
        when(bookService.getBooks(fakeAuthors, BookSearchType.AUTHORS)).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byAuthors")
                        .content(mapper.writeValueAsString(fakeAuthors))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBooksByCategories_Existant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        when(bookService.getBooks(book.getCategories(), BookSearchType.CATEGORIES)).thenReturn(books);
        when(bookMapper.toCategories(book.getCategories())).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byCategories")
                        .content(mapper.writeValueAsString(book.getCategories()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getBooksByCategories_NonExistant() throws Exception {
        List<Book> books = new ArrayList<Book>();
        String[] fakeCategories = new String[] { "Thriller" };
        when(bookService.getBooks(fakeCategories, BookSearchType.CATEGORIES)).thenReturn(books);
        when(bookMapper.toCategories(book.getCategories())).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byCategories")
                        .content(mapper.writeValueAsString(fakeCategories))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getBooksByCategories_InvalidCategories() throws Exception {
        String[] fakeCategories = new String[] { "Blah" };
        doAnswer((i) -> {
            throw new InvalidEnumException(BookExceptionMessages.INVALID_CATEGORY(fakeCategories[0].toUpperCase()));
        }).when(bookMapper).toCategories(fakeCategories);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/byCategories")
                        .content(mapper.writeValueAsString(fakeCategories))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getBook_Existant() throws Exception {
        when(bookService.getBook(book.getRefId())).thenReturn(book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + (book.getRefId())))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getBook_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND("Blah"));
        }).when(bookService).getBook("Blah");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + "Blah"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateBook_Existant() throws Exception {
        when(bookService.updateBook(book)).thenReturn(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getRefId())
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refId").value(book.getRefId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void updateBook_Invalid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getRefId())
                        .content(mapper.writeValueAsString(invalidCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateBook_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND("Blah"));
        }).when(bookService).updateBook(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + "Blah")
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void deleteBook_Existant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + (book.getRefId())))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void deleteBook_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND("Blah"));
        }).when(bookService).deleteBook("Blah");

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + "Blah"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    // Helper functions
    // ____________________________________________________
    private static BookCreationDTO toDTO(Book book) {
        BookCreationDTO dto = new BookCreationDTO();
        dto.setTitle(book.getTitle());
        dto.setNumOfPages(book.getNumOfPages());
        dto.setQuantity(book.getQuantity());
        dto.setAuthors(book.getAuthors());
        dto.setCategories(toCategories(book.getCategories()));

        return dto;
    }

    private static Category[] toCategories(String[] categs) {
        Category[] categories = new Category[categs.length];
        for (int i = 0; i < categs.length; i++) {
            try {
                categories[i] = Category.valueOf(categs[i].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidEnumException(BookExceptionMessages.INVALID_CATEGORY(categs[i].toUpperCase()));
            }

        }

        return categories;
    }
}
