package io.library.library_3.book.dtos;

import org.springframework.stereotype.Component;

import io.library.library_3.book.entity.Book;
import io.library.library_3.enums.Category;

@Component
public class BookMapper {
    // To DTO
    public BookCreationDTO toDTO(Book book) {
        String[] categs = book.getCategories();
        Category[] categories = new Category[categs.length];
        for (int i = 0; i < categs.length; i++) {
            categories[i] = Category.valueOf(categs[i]);
        }

        BookCreationDTO dto = new BookCreationDTO();
        dto.setTitle(book.getTitle());
        dto.setNumOfPages(book.getNumOfPages());
        dto.setQuantity(book.getQuantity());
        dto.setAuthors(book.getAuthors());
        dto.setCategories(categories);

        return dto;
    }

    // To entity
    public Book toBook(BookCreationDTO dto) {
        Category[] categs = dto.getCategories();
        String[] categories = new String[categs.length];
        for (int i = 0; i < categs.length; i++) {
            categories[i] = categs[i].toString();
        }

        Book book = new Book(dto.getTitle(), dto.getAuthors(),
                dto.getNumOfPages(), dto.getQuantity(), categories);

        return book;
    }
}
