package io.library.library_3.borrowed_book.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.dtos.BorrowedBookCreationDTO;
import io.library.library_3.borrowed_book.dtos.BorrowedBookReadingDTO;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.mapper.BorrowedBookMapper;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.user.dtos.UserReadingDTO;
import io.library.library_3.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("borrowing")
@Tag(name = "Borrowing")
@SecurityRequirement(name = "Authorization")
public class BorrowedBookController {
    private BorrowedBookService borrowedBookService;
    private BorrowedBookMapper borrowedBookMapper;
    private UserMapper userMapper;

    public BorrowedBookController(BorrowedBookService borrowedBookService, BorrowedBookMapper borrowedBookMapper,
            UserMapper userMapper) {
        this.borrowedBookService = borrowedBookService;
        this.borrowedBookMapper = borrowedBookMapper;
        this.userMapper = userMapper;
    }

    // Create
    @Operation(description = "POST endpoint for borrowing a book by a student.", summary = "Borrow a book (Student)")
    @PostMapping("/students/{userId}/{refId}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT') and !hasAuthority('ROLE_LIBRARIAN') and " +
            "#userId == authentication.principal.id")
    public BorrowedBookReadingDTO borrowBookStudent(
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "Student ID") @PathVariable int userId,
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Book Reference ID") @PathVariable String refId,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BorrowedBookCreationDTO") @RequestBody BorrowedBookCreationDTO dto) {
        return borrowedBookMapper
                .toDTO(borrowedBookService.borrowBook(refId, userId, dto.getDateDue(), UserTypeCustom.STUDENT));
    }

    @Operation(description = "POST endpoint for borrowing a book by a librarian.", summary = "Borrow a book (Librarian)")
    @PostMapping("/librarians/{userId}/{refId}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN') and " +
            "#userId == authentication.principal.id")
    public BorrowedBookReadingDTO borrowBookLibrarian(
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "Librarian ID") @PathVariable int userId,
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Book Reference ID") @PathVariable String refId,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BorrowedBookCreationDTO") @RequestBody BorrowedBookCreationDTO dto) {
        return borrowedBookMapper
                .toDTO(borrowedBookService.borrowBook(refId, userId, dto.getDateDue(), UserTypeCustom.LIBRARIAN));
    }

    // Read
    @Operation(description = "GET endpoint for retrieving a list of books." +
            "\n\n The books being retrieved will be in the form of Book, not BorrowedBookReadingDTO.", summary = "Get all borrowed books")
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<Book> getBooksBorrowed() {
        return borrowedBookService.getBooksBorrowed();
    }

    @Operation(description = "GET endpoint for retrieving a list of users borrowing a book, identified by its reference id.", summary = "Get all users borrowing a book")
    @GetMapping("/books/{refId}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<UserReadingDTO> getUsersBorrowingBook(
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Book Reference ID") @PathVariable String refId) {
        return userMapper.toDTO(borrowedBookService.getUsersBorrowingBook(refId));
    }

    @Operation(description = "GET endpoint for retrieving a list of books borrowed by a student, identified by their user id"
            +
            "\n\n The books being retrieved will be in the form of Book, not BorrowedBookReadingDTO.", summary = "Get all books borrowed by user (Student)")
    @GetMapping("/students/{userId}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT') and " +
            "(#userId == authentication.principal.id or hasAuthority('ROLE_LIBRARIAN'))")
    public List<Book> getBooksBorrowedByStudent(
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "Student ID") @PathVariable int userId) {
        return borrowedBookService.getBooksBorrowedByUser(userId, UserTypeCustom.STUDENT);
    }

    @Operation(description = "GET endpoint for retrieving a list of books borrowed by a librarian, identified by their user id"
            +
            "\n\n The books being retrieved will be in the form of Book, not BorrowedBookReadingDTO.", summary = "Get all books borrowed by user (Librarian)")
    @GetMapping("/librarians/{userId}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<Book> getBooksBorrowedByLibrarian(
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "Librarian ID") @PathVariable int userId) {
        return borrowedBookService.getBooksBorrowedByUser(userId, UserTypeCustom.LIBRARIAN);
    }

    // Update
    @Operation(description = "PUT endpoint for updating a single borrowed book's due date, indentified by its id.", summary = "Update Due Date")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    // FIXME: Use pre authorize instead of post authorize
    @PostAuthorize("returnObject.userId == authentication.principal.id or hasAuthority('ROLE_LIBRARIAN')")
    public BorrowedBookReadingDTO updateBorrowedBookDate(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "BorrowedBook ID") @PathVariable int id,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BorrowedBookCreationDTO") @RequestBody BorrowedBookCreationDTO dto) {
        BorrowedBook borrowedBook = borrowedBookMapper.toBorrowedBook(dto);
        borrowedBook.setId(id);

        return borrowedBookMapper.toDTO(borrowedBookService.updateBorrowedBookDate(borrowedBook));
    }

    // Delete
    @Operation(description = "DELETE endpoint for returning a borrowed book, identified by its id.", summary = "Return book")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    // TODO: Add an auth guard to ensure users can only return their own books
    public SuccessResponse returnBook(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "BorrowedBook ID") @PathVariable int id) {
        borrowedBookService.returnBook(id);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }

}
