package io.library.library_3.librarian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.librarian.dtos.LibrarianCreationDTO;
import io.library.library_3.librarian.dtos.LibrarianMapper;
import io.library.library_3.librarian.dtos.LibrarianReadingDTO;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.service.LibrarianService;
import jakarta.validation.Valid;

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
    public LibrarianReadingDTO addLibrarian(@Valid @RequestBody LibrarianCreationDTO dto) {
        Librarian librarian = librarianMapper.toLibrarian(dto);

        return librarianMapper.toReadingDTO(librarianService.addLibrarian(librarian));
    }

    // Read
    @GetMapping()
    public List<LibrarianReadingDTO> getLibrarians() {
        return librarianMapper.toReadingDTO(librarianService.getLibrarians());
    }

    @GetMapping("/{id}")
    public LibrarianReadingDTO getLibrarian(@PathVariable int id) {
        return librarianMapper.toReadingDTO(librarianService.getLibrarian(id));
    }

    // Update
    @PutMapping("/{id}")
    public LibrarianReadingDTO updateLibrarian(@PathVariable int id, @Valid @RequestBody LibrarianCreationDTO dto) {
        Librarian librarian = librarianMapper.toLibrarian(dto);
        librarian.setId(id);

        return librarianMapper.toReadingDTO(librarianService.updateLibrarian(librarian));
    }

    // Delete
    @DeleteMapping("/{id}")
    public SuccessResponse removeLibrarian(@PathVariable int id) {
        librarianService.deleteLibrarian(id);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }
}
