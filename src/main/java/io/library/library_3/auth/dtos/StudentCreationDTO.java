package io.library.library_3.auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreationDTO {
    @NotBlank(message = "A student must have a name.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String name;

    @NotBlank(message = "A student must have an address.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String address;

    @NotBlank(message = "A student must have a college.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String college;

    @NotBlank(message = "A student must have a username.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String username;

    @NotBlank(message = "A student must have a password")
    private String password;
}
