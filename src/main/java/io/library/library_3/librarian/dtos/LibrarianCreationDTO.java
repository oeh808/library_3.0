package io.library.library_3.librarian.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class LibrarianCreationDTO {
    @NotBlank(message = "A librarian must have a name.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String name;
    @PositiveOrZero(message = "A librarian cannot have less than 0 years of experience.")
    @Schema(minimum = "0")
    private int yearsOfExperience = 0;

    public LibrarianCreationDTO() {

    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
