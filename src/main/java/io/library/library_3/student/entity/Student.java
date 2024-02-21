package io.library.library_3.student.entity;

import io.library.library_3.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student extends User {
    private String college;
    private String address;
    private boolean registered = false;

    public Student() {
        super();
    }

    public Student(String username, String name, String college, String address) {
        super(username, name);
        this.college = college;
        this.address = address;
    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
