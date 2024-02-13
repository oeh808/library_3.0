package io.library.library_3.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    private String refId = "";

    @Column(unique = true)
    private String title;

    private String[] authors;

    private int numOfPages;

    private int quantity;

    private String[] categories;

    public Book() {
        super();
    }

    public Book(String title, String[] authors, int numOfPages, int quantity, String[] categories) {
        title = title.toLowerCase();
        this.title = title;
        generateRefId(title);
        this.authors = authors;
        this.numOfPages = numOfPages;
        this.quantity = quantity;
        this.categories = categories;
    }

    public void generateRefId(String title) {
        // Custom way of generating unique refId's
        for (int i = 0; i < title.length(); i++) {
            if (i % 2 == 0) {
                this.refId += title.charAt(i);
            } else {
                this.refId += i;
            }
        }
    }

    // Getters and Setters
    // ______________________________________________________________________________
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
