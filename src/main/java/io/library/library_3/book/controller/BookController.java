package io.library.library_3.book.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.book.dtos.BookCreationDTO;
import io.library.library_3.book.dtos.BookMapper;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.service.BookService;
import io.library.library_3.book.service.BookServiceImpl;
import io.library.library_3.enums.BookSearchType;
import io.library.library_3.enums.Category;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("books")
public class BookController {
    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookServiceImpl bookServiceImpl, BookMapper bookMapper) {
        this.bookService = bookServiceImpl;
        this.bookMapper = bookMapper;
    }

    // Create
    @PostMapping()
    public void addBook(@Valid @RequestBody BookCreationDTO dto) {
        bookService.createBook(bookMapper.toBook(dto));
    }

    // Read
    @GetMapping()
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/byTitle/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/byAuthors")
    public List<Book> getBooksByAuthors(@RequestBody String[] authors) {
        return bookService.getBooks(authors, BookSearchType.AUTHORS);
    }

    @GetMapping("/byCategories")
    public List<Book> getBooksByCategories(@RequestBody Category[] categories) {
        return bookService.getBooks(bookMapper.fromCategories(categories), BookSearchType.CATEGORIES);
    }

    @GetMapping("/{refId}")
    public Book getBook(@PathVariable String refId) {
        return bookService.getBook(refId);
    }

    // Update
    @PutMapping("/{refId}")
    public Book updateBook(@PathVariable String refId, @Valid @RequestBody BookCreationDTO dto) {
        return bookService.updateBook(bookMapper.toBook(dto));
    }

    // Delete
    @DeleteMapping("path/{refId}")
    public void deleteBook(@PathVariable String refId) {
        bookService.deleteBook(refId);
    }
}
