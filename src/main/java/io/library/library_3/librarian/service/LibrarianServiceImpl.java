package io.library.library_3.librarian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.repo.LibrarianRepo;

@Service
public class LibrarianServiceImpl implements LibrarianService {
    private LibrarianRepo librarianRepo;

    public LibrarianServiceImpl(LibrarianRepo librarianRepo) {
        this.librarianRepo = librarianRepo;
    }

    @Override
    public Librarian addLibrarian(Librarian librarian) {
        return librarianRepo.save(librarian);
    }

    @Override
    public List<Librarian> getLibrarians() {
        return librarianRepo.findAll();
    }

    @Override
    public Librarian getLibrarian(int id) {
        Optional<Librarian> opLibrarian = librarianRepo.findById(id);
        if (opLibrarian.isPresent()) {
            return opLibrarian.get();
        } else {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(id));
        }
    }

    @Override
    public Librarian updateLibrarian(Librarian librarian) {
        getLibrarian(librarian.getId());
        return librarianRepo.save(librarian);
    }

    @Override
    public void deleteLibrarian(int id) {
        getLibrarian(id);
        librarianRepo.deleteById(id);
    }

}
