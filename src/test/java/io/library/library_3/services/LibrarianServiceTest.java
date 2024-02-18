package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.librarian.entity.Librarian;
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

    @Autowired
    LibrarianService librarianService;

    private static Librarian librarian;

    @BeforeAll
    public static void setUp() {
        librarian = new Librarian("Craig Robinson", 3);
        librarian.setId(100);
    }

    @Test
    public void addLibrarian() {
        when(librarianRepo.save(librarian)).thenReturn(librarian);

        assertEquals(librarian, librarianService.addLibrarian(librarian));
    }

    @Test
    public void getLibrarians() {
        List<Librarian> librarians = new ArrayList<Librarian>();
        librarians.add(librarian);
        when(librarianRepo.findAll()).thenReturn(librarians);

        assertEquals(librarians, librarianService.getLibrarians());
    }

    @Test
    public void getLibrarian_Existant() {
        when(librarianRepo.findById(librarian.getId())).thenReturn(Optional.of(librarian));

        assertEquals(librarian, librarianService.getLibrarian(librarian.getId()));
    }

    @Test
    public void getLibrarian_NonExistant() {
        when(librarianRepo.findById(librarian.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    librarianService.getLibrarian(librarian.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1)));
    }

    @Test
    public void updateLibrarian_Existant() {
        when(librarianRepo.findById(librarian.getId())).thenReturn(Optional.of(librarian));
        when(librarianRepo.save(librarian)).thenReturn(librarian);

        assertEquals(librarian, librarianService.updateLibrarian(librarian));
    }

    @Test
    public void updateLibrarian_NonExistant() {
        when(librarianRepo.findById(librarian.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    Librarian someLibrarian = new Librarian();
                    someLibrarian.setId(librarian.getId() - 1);
                    librarianService.updateLibrarian(someLibrarian);
                });
        assertTrue(ex.getMessage().contains(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1)));
    }

    @Test
    public void deleteLibrarian_Existant() {
        when(librarianRepo.findById(librarian.getId())).thenReturn(Optional.of(librarian));

        librarianService.deleteLibrarian(librarian.getId());

        verify(librarianRepo, times(1)).deleteById(librarian.getId());
    }

    @Test
    public void deleteLibrarian_NonExistant() {
        when(librarianRepo.findById(librarian.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    librarianService.deleteLibrarian(librarian.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1)));
    }
}
