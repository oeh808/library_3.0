package io.library.library_3.user.entity;

import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue
    @Column(name = "user_id")
    private int id;
    private String name;
    @OneToMany
    private List<BorrowedBook> borrowedBooks;

    public User() {
        this.borrowedBooks = new ArrayList<BorrowedBook>();
    }

    public User(String name) {
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
