package io.library.library_3.book.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.book.entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
