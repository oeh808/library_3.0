package io.library.library_3.student.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class StudentUpdateDTO {
    @NotBlank(message = "A student must have a name.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String name;
    @NotBlank(message = "A student must have an address.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String address;
    @NotBlank(message = "A student must have a college.")
    @Schema(requiredProperties = { "Cannot be null", "Cannot be empty" })
    private String college;

    public StudentUpdateDTO() {

    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
