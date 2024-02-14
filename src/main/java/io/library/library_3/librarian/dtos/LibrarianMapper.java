package io.library.library_3.librarian.dtos;

import java.util.ArrayList;
import java.util.List;

import io.library.library_3.librarian.entity.Librarian;

public class LibrarianMapper {
    // To DTO
    public LibrarianCreationDTO toCreationDTO(Librarian librarian) {
        LibrarianCreationDTO dto = new LibrarianCreationDTO();
        dto.setName(librarian.getName());
        dto.setYearsOfExperience(librarian.getYearsOfExperience());

        return dto;
    }

    public LibrarianReadingDTO toReadingDTO(Librarian librarian) {
        LibrarianReadingDTO dto = new LibrarianReadingDTO(librarian.getId(), librarian.getName(),
                librarian.getYearsOfExperience());

        return dto;
    }

    public List<LibrarianReadingDTO> toReadingDTO(List<Librarian> librarians) {
        List<LibrarianReadingDTO> dtos = new ArrayList<LibrarianReadingDTO>();
        for (Librarian librarian : librarians) {
            dtos.add(toReadingDTO(librarian));
        }

        return dtos;
    }

    // To Entity
    public Librarian toLibrarian(LibrarianCreationDTO dto) {
        Librarian librarian = new Librarian(dto.getName(), dto.getYearsOfExperience());

        return librarian;
    }
}
