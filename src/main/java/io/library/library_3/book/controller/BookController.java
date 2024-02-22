package io.library.library_3.book.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.book.dtos.BookCreationDTO;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.mapper.BookMapper;
import io.library.library_3.book.service.BookService;
import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.enums.BookSearchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("books")
@Tag(name = "Books")
@SecurityRequirement(name = "Authorization")
public class BookController {
    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    // Create
    @Operation(description = "POST endpoint for creating a book.", summary = "Create a book")
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public Book addBook(
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BookCreationDTO") @RequestBody BookCreationDTO dto) {
        return bookService.createBook(bookMapper.toBook(dto));
    }

    // Read
    @Operation(description = "GET endpoint for retrieving a list of books.", summary = "Get all books")
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @Operation(description = "GET endpoint for retrieving a list of books INCLUDING a regex.", summary = "Get all books by title regex")
    @GetMapping("/byTitle/{title}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public List<Book> getBooksByTitle(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Title Regex") @PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @Operation(description = "GET endpoint for retrieving a list of books INCLUDING a list of authors.", summary = "Get all books by authors")
    @GetMapping("/byAuthors")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public List<Book> getBooksByAuthors(@Parameter(name = "Authors") @RequestBody String[] authors) {
        return bookService.getBooks(authors, BookSearchType.AUTHORS);
    }

    @Operation(description = "GET endpoint for retrieving a list of books INCLUDING a list of categories.", summary = "Get all books by categories")
    @GetMapping("/byCategories")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public List<Book> getBooksByCategories(
            @Parameter(name = "Categories", description = "Must be comprised of valid categories (See BookCreationDTO)") @RequestBody String[] categories) {
        bookMapper.toCategories(categories); // Will throw error if categories cannot be converted
        return bookService.getBooks(categories, BookSearchType.CATEGORIES);
    }

    @Operation(description = "GET endpoint for retrieving a single book by its reference id (refId).", summary = "Get book by refId")
    @GetMapping("/{refId}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public Book getBook(
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Reference ID") @PathVariable String refId) {
        return bookService.getBook(refId);
    }

    // Update
    @Operation(description = "PUT endpoint for updating a single book by its reference id.", summary = "Update book")
    @PutMapping("/{refId}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public Book updateBook(
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Reference ID") @PathVariable String refId,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BookCreationDTO") @RequestBody BookCreationDTO dto) {
        Book book = bookMapper.toBook(dto);
        book.setRefId(refId);

        return bookService.updateBook(book);
    }

    // Delete
    @Operation(description = "DELETE endpoint for deleting a book by its reference id.", summary = "Delete book")
    @DeleteMapping("/{refId}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public SuccessResponse deleteBook(
            @Parameter(in = ParameterIn.PATH, name = "refId", description = "Reference ID") @PathVariable String refId) {
        bookService.deleteBook(refId);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }
}
