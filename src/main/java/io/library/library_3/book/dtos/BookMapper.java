package io.library.library_3.book.dtos;

import org.springframework.stereotype.Component;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.enums.Category;
import io.library.library_3.error_handling.exceptions.InvalidEnumException;

@Component
public class BookMapper {
    // To DTO
    public BookCreationDTO toDTO(Book book) {
        BookCreationDTO dto = new BookCreationDTO();
        dto.setTitle(book.getTitle());
        dto.setNumOfPages(book.getNumOfPages());
        dto.setQuantity(book.getQuantity());
        dto.setAuthors(book.getAuthors());
        dto.setCategories(toCategories(book.getCategories()));

        return dto;
    }

    // To entity
    public Book toBook(BookCreationDTO dto) {
        Book book = new Book(dto.getTitle(), dto.getAuthors(),
                dto.getNumOfPages(), dto.getQuantity(), fromCategories(dto.getCategories()));

        return book;
    }

    // Custom
    // __________________________________________________
    public String[] fromCategories(Category[] categs) {
        String[] categories = new String[categs.length];
        for (int i = 0; i < categs.length; i++) {
            categories[i] = categs[i].toString();
        }

        return categories;
    }

    public Category[] toCategories(String[] categs) {
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
