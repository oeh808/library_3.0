package io.library.library_3.librarian.dtos;

import io.library.library_3.librarian.entity.Librarian;

public class LibrarianMapper {
    // To DTO
    public LibrarianCreationDTO toDTO(Librarian librarian) {
        LibrarianCreationDTO dto = new LibrarianCreationDTO();
        dto.setName(librarian.getName());
        dto.setYearsOfExperience(librarian.getYearsOfExperience());

        return dto;
    }

    // To Entity
    public Librarian toLibrarian(LibrarianCreationDTO dto) {
        Librarian librarian = new Librarian(dto.getName(), dto.getYearsOfExperience());

        return librarian;
    }
}
