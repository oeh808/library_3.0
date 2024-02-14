package io.library.library_3.borrowed_book.mapper;

import org.springframework.stereotype.Component;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.dtos.BorrowedBookCreationDTO;
import io.library.library_3.borrowed_book.dtos.BorrowedBookReadingDTO;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.user.entity.User;

@Component
public class BorrowedBookMapper {
    // To DTO
    public BorrowedBookReadingDTO toDTO(BorrowedBook borrowedBook) {
        Book book = borrowedBook.getBook();
        User user = borrowedBook.getUser();

        BorrowedBookReadingDTO dto = new BorrowedBookReadingDTO(book.getRefId(), user.getId(),
                borrowedBook.getDateDue());

        return dto;
    }

    // To Entity
    public BorrowedBook toBorrowedBook(BorrowedBookCreationDTO dto) {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setDateDue(dto.getDateDue());

        return borrowedBook;
    }
}
