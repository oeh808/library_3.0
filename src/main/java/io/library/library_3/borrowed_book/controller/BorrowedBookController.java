package io.library.library_3.borrowed_book.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.dtos.BorrowedBookCreationDTO;
import io.library.library_3.borrowed_book.dtos.BorrowedBookMapper;
import io.library.library_3.borrowed_book.dtos.BorrowedBookReadingDTO;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.enums.UserTypeCustom;
import io.library.library_3.user.entity.dtos.UserMapper;
import io.library.library_3.user.entity.dtos.UserReadingDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("borrowing")
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
    @PostMapping("/students/{userId}/{refId}")
    public BorrowedBookReadingDTO borrowBookStudent(@PathVariable int userId, @PathVariable String refId,
            @Valid @RequestBody BorrowedBookCreationDTO dto) {
        return borrowedBookMapper
                .toDTO(borrowedBookService.borrowBook(refId, userId, dto.getDateDue(), UserTypeCustom.STUDENT));
    }

    @PostMapping("/librarians/{userId}/{refId}")
    public BorrowedBookReadingDTO borrowBookLibrarian(@PathVariable int userId, @PathVariable String refId,
            @Valid @RequestBody BorrowedBookCreationDTO dto) {
        return borrowedBookMapper
                .toDTO(borrowedBookService.borrowBook(refId, userId, dto.getDateDue(), UserTypeCustom.LIBRARIAN));
    }

    // Read
    @GetMapping()
    public List<Book> getBooksBorrowed() {
        return borrowedBookService.getBooksBorrowed();
    }

    @GetMapping("/books/{refId}")
    public List<UserReadingDTO> getUsersBorrowingBook(@PathVariable String refId) {
        return userMapper.toDTO(borrowedBookService.getUsersBorrowingBook(refId));
    }

    @GetMapping("/students/{userId}")
    public List<Book> getBooksBorrowedByStudent(@PathVariable int userId) {
        return borrowedBookService.getBooksBorrowedByUser(userId, UserTypeCustom.STUDENT);
    }

    @GetMapping("/librarians/{userId}")
    public List<Book> getBooksBorrowedByLibrarian(@PathVariable int userId) {
        return borrowedBookService.getBooksBorrowedByUser(userId, UserTypeCustom.LIBRARIAN);
    }

    // Update
    @PutMapping("/students/{id}")
    public BorrowedBookReadingDTO updateStudentBorrowedBookDate(@PathVariable int id,
            @Valid @RequestBody BorrowedBookCreationDTO dto) {
        BorrowedBook borrowedBook = borrowedBookMapper.toBorrowedBook(dto);
        borrowedBook.setId(id);

        return borrowedBookMapper
                .toDTO(borrowedBookService.updateBorrowedBookDate(borrowedBook, UserTypeCustom.STUDENT));
    }

    @PutMapping("/librarians/{id}")
    public BorrowedBookReadingDTO updateLibrarianBorrowedBookDate(@PathVariable int id,
            @Valid @RequestBody BorrowedBookCreationDTO dto) {
        BorrowedBook borrowedBook = borrowedBookMapper.toBorrowedBook(dto);
        borrowedBook.setId(id);

        return borrowedBookMapper
                .toDTO(borrowedBookService.updateBorrowedBookDate(borrowedBook, UserTypeCustom.LIBRARIAN));
    }

    // Delete
    @DeleteMapping("/{id}")
    public void returnBook(@PathVariable int id) {
        borrowedBookService.returnBook(id);
    }

}