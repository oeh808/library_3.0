package io.library.library_3.student.dtos;

public class StudentReadingDTO {
    private int id;
    private String name;
    private String address;
    private String college;
    private boolean registered;

    public StudentReadingDTO() {

    }

    public StudentReadingDTO(int id, String name, String address, String college, boolean registered) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.college = college;
        this.registered = registered;
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

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
