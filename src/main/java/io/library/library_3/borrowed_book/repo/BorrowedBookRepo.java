package io.library.library_3.borrowed_book.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.library.library_3.book.entity.Book;
import io.library.library_3.borrowed_book.entity.BorrowedBook;
import io.library.library_3.user.entity.User;

public interface BorrowedBookRepo extends JpaRepository<BorrowedBook, Integer> {
    @Query("SELECT DISTINCT bb.book FROM BorrowedBook bb")
    public List<Book> findAllBooks();

    @Query("SELECT bb.user FROM BorrowedBook bb " +
            "WHERE bb.book = :book")
    public List<User> findUsersBorrowingBook(@Param("book") Book book);

    @Query("SELECT bb.book FROM BorrowedBook bb" +
            "WHERE bb.user = :user")
    public List<Book> findBooksBorrowedByUser(@Param("user") User user);
}
