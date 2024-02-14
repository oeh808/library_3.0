package io.library.library_3.librarian.dtos;

public class LibrarianReadingDTO {
    private int id;
    private String name;
    private int yearsOfExperience;

    public LibrarianReadingDTO() {

    }

    public LibrarianReadingDTO(int id, String name, int yearsOfExperience) {
        this.id = id;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters and Setters
    // ______________________________________________________________________________
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
