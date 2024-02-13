package io.library.library_3.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.book.entity.Book;
import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.enums.BookSearchType;
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
    public void createBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        title.toLowerCase();
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
    public Book getBook(int id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            // Book does not exist with given id
            return null;
        }
    }

    @Override
    public Book getBookByRefId(String refId) {
        Optional<Book> book = bookRepo.findByRefId(refId);
        if (book.isPresent()) {
            return book.get();
        } else {
            // Book does not exist with given refId
            return null;
        }
    }

    @Override
    public Book updateBook(Book book) {
        bookRepo.save(book);
        return getBook(book.getId());
    }

    @Override
    public void deleteBook(int id) {
        bookRepo.deleteById(id);
    }

}
