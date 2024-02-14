package io.library.library_3.librarian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.librarian.dtos.LibrarianCreationDTO;
import io.library.library_3.librarian.dtos.LibrarianMapper;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.service.LibrarianService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("librarians")
public class LibrarianController {
    private LibrarianService librarianService;
    private LibrarianMapper librarianMapper;

    public LibrarianController(LibrarianService librarianService, LibrarianMapper librarianMapper) {
        this.librarianService = librarianService;
        this.librarianMapper = librarianMapper;
    }

    // Create
    @PostMapping()
    public Librarian addLibrarian(@RequestBody LibrarianCreationDTO dto) {
        Librarian librarian = librarianMapper.toLibrarian(dto);

        return librarianService.addLibrarian(librarian);
    }

    // Read
    @GetMapping()
    public List<Librarian> getLibrarians() {
        return librarianService.getLibrarians();
    }

    @GetMapping("/{id}")
    public Librarian getLibrarian(@PathVariable int id) {
        return librarianService.getLibrarian(id);
    }

    // Update
    @PutMapping("/{id}")
    public Librarian updateLibrarian(@PathVariable int id, @RequestBody LibrarianCreationDTO dto) {
        Librarian librarian = librarianMapper.toLibrarian(dto);
        librarian.setId(id);

        return librarianService.updateLibrarian(librarian);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void removeLibrarian(@PathVariable int id) {
        librarianService.deleteLibrarian(id);
    }
}
