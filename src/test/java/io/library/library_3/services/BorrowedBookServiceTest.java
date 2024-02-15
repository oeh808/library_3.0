package io.library.library_3.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.book.repo.BookRepo;
import io.library.library_3.borrowed_book.repo.BorrowedBookRepo;
import io.library.library_3.borrowed_book.service.BorrowedBookService;
import io.library.library_3.borrowed_book.service.BorrowedBookServiceImpl;
import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.student.repo.StudentRepo;

@ExtendWith(SpringExtension.class)
public class BorrowedBookServiceTest {
    // TODO: Complete unit test

    @TestConfiguration
    static class BorrowedBookServiceTestConfig {
        @Bean
        @Autowired
        BorrowedBookService borrowedBookService(BorrowedBookRepo borrowedBookRepo, LibrarianRepo librarianRepo,
                StudentRepo studentRepo, BookRepo bookRepo) {
            return new BorrowedBookServiceImpl(borrowedBookRepo, librarianRepo, studentRepo, bookRepo);
        }
    }

    @MockBean
    private BorrowedBookRepo borrowedBookRepo;

    @MockBean
    private LibrarianRepo librarianRepo;

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private BookRepo bookRepo;

    @Autowired
    private BorrowedBookService borrowedBookService;

    @BeforeAll
    public static void setUp() {

    }
}
