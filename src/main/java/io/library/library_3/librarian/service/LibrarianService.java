package io.library.library_3.librarian.service;

import java.util.List;

import io.library.library_3.librarian.entity.Librarian;

public interface LibrarianService {
    // Create
    public Librarian addLibrarian(Librarian librarian);

    // Read
    public List<Librarian> getLibrarians();

    public Librarian getLibrarian(int id);

    // Update
    public Librarian updateLibrarian(Librarian librarian);

    // Delete
    public void deleteLibrarian(int id);
}
