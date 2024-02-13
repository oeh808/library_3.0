package io.library.library_3.librarian.entity;

import io.library.library_3.user.entity.User;
import jakarta.persistence.Entity;

@Entity
public class Librarian extends User {
    private int yearsOfExperience;

    public Librarian() {
        super();
    }

    public Librarian(String name, int yearsOfExperience) {
        super(name);
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters and Setters
    // ______________________________________________________________________________
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
