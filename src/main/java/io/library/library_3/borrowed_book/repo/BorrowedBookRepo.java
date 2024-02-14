package io.library.library_3.borrowed_book.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;

public interface BorrowedBookRepo extends JpaRepository<BorrowedBook, Integer> {
    @Query("SELECT DISTINCT bb.book FROM BorrowedBook bb")
    public List<Book> findAllBooks();
}
