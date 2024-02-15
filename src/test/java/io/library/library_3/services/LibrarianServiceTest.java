package io.library.library_3.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.librarian.repo.LibrarianRepo;
import io.library.library_3.librarian.service.LibrarianService;
import io.library.library_3.librarian.service.LibrarianServiceImpl;

@ExtendWith(SpringExtension.class)
public class LibrarianServiceTest {

    @TestConfiguration
    static class LibrarianServiceTestConfig {
        @Bean
        @Autowired
        LibrarianService librarianService(LibrarianRepo librarianRepo) {
            return new LibrarianServiceImpl(librarianRepo);
        }
    }

    @MockBean
    private LibrarianRepo librarianRepo;

    @BeforeAll
    public static void setUp() {

    }
}
