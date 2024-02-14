package io.library.library_3.user.entity.dtos;

public class UserReadingDTO {
    private int id;
    private String name;

    public UserReadingDTO() {

    }

    public UserReadingDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

}
