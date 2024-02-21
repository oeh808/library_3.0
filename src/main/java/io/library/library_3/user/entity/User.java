package io.library.library_3.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import io.library.library_3.borrowed_book.entity.BorrowedBook;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence"),
            @Parameter(name = "initial_value", value = "100"),
            @Parameter(name = "increment_size", value = "1")
    })
    @Column(name = "user_id")
    private int id;
    private String username;
    private String name;
    @OneToMany
    private List<BorrowedBook> borrowedBooks;

    public User() {
        this.borrowedBooks = new ArrayList<BorrowedBook>();
    }

    public User(String username, String name) {
        this.username = username;
        this.name = name;
        this.borrowedBooks = new ArrayList<BorrowedBook>();
    }

    // Getters and Setters
    // ______________________________________________________________________________
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBorrowedBook(BorrowedBook borrowedBook) {
        this.borrowedBooks.add(borrowedBook);
    }

    public void removeBorrowedBook(BorrowedBook borrowedBook) {
        this.borrowedBooks.remove(borrowedBook);
    }

}
