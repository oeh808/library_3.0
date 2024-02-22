package io.library.library_3.librarian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.librarian.dtos.LibrarianUpdateDTO;
import io.library.library_3.librarian.dtos.LibrarianReadingDTO;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.mapper.LibrarianMapper;
import io.library.library_3.librarian.service.LibrarianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("librarians")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Librarians")
public class LibrarianController {
    private LibrarianService librarianService;
    private LibrarianMapper librarianMapper;

    public LibrarianController(LibrarianService librarianService, LibrarianMapper librarianMapper) {
        this.librarianService = librarianService;
        this.librarianMapper = librarianMapper;
    }

    // Read
    @Operation(description = "GET endpoint for retrieving a list of librarians." +
            "\n\n Can only be done by librarians.", summary = "Get all librarians")
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<LibrarianReadingDTO> getLibrarians() {
        return librarianMapper.toReadingDTO(librarianService.getLibrarians());
    }

    @Operation(description = "GET endpoint for retrieving a single librarian by their id." +
            "\n\n Can only be done by librarians.", summary = "Get librarian by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public LibrarianReadingDTO getLibrarian(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Librarian ID") @PathVariable int id) {
        return librarianMapper.toReadingDTO(librarianService.getLibrarian(id));
    }

    // Update
    @Operation(description = "PUT endpoint for updating a single librarian by their id." +
            "\n\n Can only be done by librarians.", summary = "Update librarian")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of LibrarianUpdateDTO")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public LibrarianReadingDTO updateLibrarian(@PathVariable int id,
            @Valid @RequestBody LibrarianUpdateDTO dto) {
        Librarian librarian = librarianMapper.toLibrarian(dto);
        librarian.setId(id);

        return librarianMapper.toReadingDTO(librarianService.updateLibrarian(librarian));
    }

    // Delete
    @Operation(description = "DELETE endpoint for deleting a librarian by their id." +
            "\n\n Can only be done by librarians.", summary = "Delete librarian")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public SuccessResponse removeLibrarian(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Librarian ID") @PathVariable int id) {
        librarianService.deleteLibrarian(id);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }
}
