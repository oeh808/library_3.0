package io.library.library_3.student.dtos;

import jakarta.validation.constraints.NotBlank;

public class StudentRegisterationDTO {
    @NotBlank(message = "A student must have a name.")
    private String name;
    @NotBlank(message = "A student must have an address.")
    private String address;
    @NotBlank(message = "A student must have a college.")
    private String college;

    public StudentRegisterationDTO() {

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
