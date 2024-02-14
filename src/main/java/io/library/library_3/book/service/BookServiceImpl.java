package io.library.library_3.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.book.BookExceptionMessages;
import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.enums.BookSearchType;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.search.LinearSearchService;
import io.library.library_3.search.SearchService;

@Service
public class BookServiceImpl implements BookService {
    private BookRepo bookRepo;
    private SearchService searchService;

    public BookServiceImpl(BookRepo bookRepo, LinearSearchService searchService) {
        this.bookRepo = bookRepo;
        this.searchService = searchService;
    }

    @Override
    public Book createBook(Book book) {
        Optional<Book> opBook = bookRepo.findById(book.getRefId());
        if (opBook.isPresent()) {
            throw new DuplicateEntityException(
                    BookExceptionMessages.BOOK_WITH_TITLE_EXISTS("(" + book.getTitle() + ")"));
        }

        return bookRepo.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepo.findByTitleContaining(title);
    }

    @Override
    public List<Book> getBooks(String[] arr, BookSearchType bookSearchType) {
        List<Book> books = bookRepo.findAll();
        List<Book> res = new ArrayList<Book>();

        for (Book book : books) {
            if (bookSearchType.equals(BookSearchType.AUTHORS)) {
                if (searchService.search(book.getAuthors(), arr))
                    res.add(book);
            } else {
                if (searchService.search(book.getCategories(), arr))
                    res.add(book);
            }
        }

        return res;
    }

    @Override
    public Book getBook(String refId) {
        Optional<Book> book = bookRepo.findById(refId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new EntityNotFoundException(BookExceptionMessages.REFID_NOT_FOUND(refId));
        }
    }

    @Override
    public Book updateBook(Book book) {
        getBook(book.getRefId());
        return bookRepo.save(book);
    }

    @Override
    public void deleteBook(String refId) {
        getBook(refId);
        bookRepo.deleteById(refId);
    }

}
