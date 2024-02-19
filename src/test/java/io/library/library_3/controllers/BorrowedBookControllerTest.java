package io.library.library_3.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.BorrowedBookExceptionMessages;
import io.library.library_3.borrowed_book.controller.BorrowedBookController;
import io.library.library_3.borrowed_book.dtos.BorrowedBookCreationDTO;
import io.library.library_3.borrowed_book.dtos.BorrowedBookReadingDTO;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.mapper.BorrowedBookMapper;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.error_handling.exceptions.OutOfStockException;
import io.library.library_3.error_handling.exceptions.UnauthorizedActionException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.student.StudentExceptionMessages;
import io.library.library_3.student.entity.Student;
import io.library.library_3.user.dtos.UserReadingDTO;
import io.library.library_3.user.entity.User;
import io.library.library_3.user.mapper.UserMapper;

@ActiveProfiles("test")
@WebMvcTest(BorrowedBookController.class)
public class BorrowedBookControllerTest {
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
    private static UserReadingDTO userReadingDTO;
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
        userReadingDTO = new UserReadingDTO(user.getId(), user.getName());

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
        when(borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.LIBRARIAN))
                .thenReturn(borrowedBook);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void borrowBook_SuccessStudent() throws Exception {
        when(borrowedBookService.borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT))
                .thenReturn(borrowedBook);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void borrowBook_Invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(invalidCreationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(invalidCreationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void borrowBook_BookNonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND(book.getRefId()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);

        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND(book.getRefId()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.LIBRARIAN);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: " + BookExceptionMessages.REFID_NOT_FOUND(book.getRefId())));
        ;

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: " + BookExceptionMessages.REFID_NOT_FOUND(book.getRefId())));
    }

    @Test
    public void borrowBook_UserNonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(user.getId()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);

        doAnswer((i) -> {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(user.getId()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.LIBRARIAN);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: " + StudentExceptionMessages.ID_NOT_FOUND(user.getId())));

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: " + LibrarianExceptionMessages.ID_NOT_FOUND(user.getId())));
    }

    @Test
    public void borrowBook_ActionNotAllowed() throws Exception {
        doAnswer((i) -> {
            throw new UnauthorizedActionException(StudentExceptionMessages.NOT_REGISTERED);
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{}"));
    }

    @Test
    public void borrowBook_BookAlreadyBorrowedByUser() throws Exception {
        doAnswer((i) -> {
            throw new DuplicateEntityException(BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);

        doAnswer((i) -> {
            throw new DuplicateEntityException(BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.LIBRARIAN);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: "
                                        + BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle())));

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: "
                                        + BorrowedBookExceptionMessages.BOOK_ALREADY_BORROWED(book.getTitle())));
    }

    @Test
    public void borrowBook_BookOutOfStock() throws Exception {
        doAnswer((i) -> {
            throw new OutOfStockException(BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.STUDENT);

        doAnswer((i) -> {
            throw new OutOfStockException(BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle()));
        }).when(borrowedBookService).borrowBook(book.getRefId(), user.getId(), date, UserTypeCustom.LIBRARIAN);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/students/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: "
                                        + BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle())));

        mockMvc.perform(MockMvcRequestBuilders.post("/borrowing/librarians/" + user.getId() + "/" + book.getRefId())
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"))
                .andExpect(
                        jsonPath("$.message")
                                .value("ERROR: "
                                        + BorrowedBookExceptionMessages.BOOK_OUT_OF_STOCK(book.getTitle())));
    }

    @Test
    public void getBooksBorrowed() throws Exception {
        when(borrowedBookService.getBooksBorrowed()).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getUsersBorrowingBook_BookExistant() throws Exception {
        List<User> users = new ArrayList<User>();
        users.add(user);
        List<UserReadingDTO> expectedRes = new ArrayList<UserReadingDTO>();
        expectedRes.add(userReadingDTO);
        when(borrowedBookService.getUsersBorrowingBook(book.getRefId())).thenReturn(users);
        when(userMapper.toDTO(users)).thenReturn(expectedRes);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/books/" + (book.getRefId())))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getUsersBorrowingBook_BookNonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND("Blah"));
        }).when(borrowedBookService).getUsersBorrowingBook("Blah");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/books/" + "Blah"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getBooksBorrowedByUser_UserExistant() throws Exception {
        when(borrowedBookService.getBooksBorrowedByUser(user.getId(), UserTypeCustom.STUDENT)).thenReturn(books);
        when(borrowedBookService.getBooksBorrowedByUser(user.getId(), UserTypeCustom.LIBRARIAN)).thenReturn(books);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/students/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/librarians/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getBooksBorrowedByUser_StudentNonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(user.getId() - 1));
        }).when(borrowedBookService).getBooksBorrowedByUser(user.getId() - 1, UserTypeCustom.STUDENT);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/students/" + (user.getId() - 1)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getBooksBorrowedByUser_LibrarianNonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(user.getId() - 1));
        }).when(borrowedBookService).getBooksBorrowedByUser(user.getId() - 1, UserTypeCustom.LIBRARIAN);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/borrowing/librarians/" + (user.getId() - 1)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateBorrowedBookDate_Existant() throws Exception {
        when(borrowedBookService.updateBorrowedBookDate(borrowedBook)).thenReturn(borrowedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/borrowing/" + borrowedBook.getId())
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refId").value(book.getRefId()))
                .andExpect(jsonPath("$.userId").value(user.getId()));
    }

    @Test
    public void updateBorrowedBookDate_Invalid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/borrowing/" + borrowedBook.getId())
                        .content(mapper.writeValueAsString(invalidCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateBorrowedBookDate_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BorrowedBookExceptionMessages.ID_NOT_FOUND(borrowedBook.getId()));
        }).when(borrowedBookService).updateBorrowedBookDate(borrowedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/borrowing/" + borrowedBook.getId())
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void returnBook_Existant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/borrowing/" + borrowedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void returnBook_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(BorrowedBookExceptionMessages.ID_NOT_FOUND(borrowedBook.getId()));
        }).when(borrowedBookService).returnBook(borrowedBook.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/borrowing/" + borrowedBook.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }
}
