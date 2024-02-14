package io.library.library_3.book.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.book.entity.Book;

public interface BookRepo extends JpaRepository<Book, String> {

    public List<Book> findByTitleContainingIgnoreCase(String title);
}
