package io.library.library_3.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.book.service.BookService;
import io.library.library_3.book.service.BookServiceImpl;
import io.library.library_3.search.LinearSearchService;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @TestConfiguration
    static class BookServiceTestConfig {
        @Bean
        @Autowired
        BookService bookService(BookRepo bookRepo, LinearSearchService searchService) {
            return new BookServiceImpl(bookRepo, new LinearSearchService());
        }
    }

    @MockBean
    private BookRepo bookRepo;

    @MockBean
    private LinearSearchService searchService;

    @BeforeAll
    public static void setUp() {

    }

}
