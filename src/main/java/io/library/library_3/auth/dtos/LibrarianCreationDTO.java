package io.library.library_3.auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianCreationDTO {
    @NotBlank(message = "A librarian must have a name.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String name;

    @PositiveOrZero(message = "A librarian cannot have less than 0 years of experience.")
    @Schema(minimum = "0")
    private int yearsOfExperience = 0;

    @NotBlank(message = "A librarian must have a username.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String username;

    @NotBlank(message = "A librarian must have a password")
    private String password;
}
