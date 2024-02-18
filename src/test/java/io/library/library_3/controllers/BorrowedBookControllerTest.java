package io.library.library_3.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.controller.BorrowedBookController;
import io.library.library_3.borrowed_book.dtos.BorrowedBookCreationDTO;
import io.library.library_3.borrowed_book.dtos.BorrowedBookReadingDTO;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.mapper.BorrowedBookMapper;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.student.entity.Student;
import io.library.library_3.user.entity.User;
import io.library.library_3.user.mapper.UserMapper;

@WebMvcTest(BorrowedBookController.class)
public class BorrowedBookControllerTest {
    // TODO: Complete Controller Unit Test

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowedBookService borrowedBookService;

    @MockBean
    private BorrowedBookMapper borrowedBookMapper;

    @MockBean
    private UserMapper userMapper;

    ObjectMapper mapper = new ObjectMapper();

    private static User user;
    private static Book book;
    private static Date date;

    private static BorrowedBookCreationDTO invalidCreationDTO;

    private static BorrowedBook borrowedBook;
    private static BorrowedBookCreationDTO creationDTO;
    private static BorrowedBookReadingDTO readingDTO;

    private static List<Book> books;

    @BeforeAll
    public static void setUp() {
        user = new Student("Inmo Bob", "Arts", "Selly Oak");
        user.setId(100);
        book = new Book("War and Peace", new String[] { "Leo Tolstoy" }, 1255, 5,
                new String[] { "FICTION", "NONFICTION" });

        date = Date.valueOf("2030-09-15");

        invalidCreationDTO = new BorrowedBookCreationDTO();
        invalidCreationDTO.setDateDue(Date.valueOf("1980-09-15"));

        borrowedBook = new BorrowedBook(book, user, date);
        borrowedBook.setId(1);
        creationDTO = new BorrowedBookCreationDTO();
        creationDTO.setDateDue(date);
        readingDTO = new BorrowedBookReadingDTO(book.getRefId(), user.getId(), date);

        books = new ArrayList<Book>();
        books.add(book);
    }

    @BeforeEach
    public void setUpMocks() {
        when(borrowedBookMapper.toDTO(any(BorrowedBook.class))).thenReturn(readingDTO);
        when(borrowedBookMapper.toBorrowedBook(any(BorrowedBookCreationDTO.class))).thenReturn(borrowedBook);
    }

    @Test
    public void borrowBook_SuccessLibrarian() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_BookNonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_UserNonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_ActionNotAllowed() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_BookAlreadyBorrowedByUser() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_BookOutOfStock() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void borrowBook_SuccessStudent() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getBooksBorrowed() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getUsersBorrowingBook_BookExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getUsersBorrowingBook_BookNonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getBooksBorrowedByUser_UserExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getBooksBorrowedByUser_StudentNonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void getBooksBorrowedByUser_LibrarianNonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void updateBorrowedBookDate_Existant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void updateBorrowedBookDate_NonExistant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void returnBook_Existant() throws Exception {
        // TODO: Complete test
    }

    @Test
    public void returnBook_NonExistant() throws Exception {
        // TODO: Complete test
    }
}
