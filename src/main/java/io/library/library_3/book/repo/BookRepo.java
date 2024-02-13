package io.library.library_3.book.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.book.entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

    public List<Book> findByTitleContaining(String title);

    public Optional<Book> findByRefId(String refId);
}
