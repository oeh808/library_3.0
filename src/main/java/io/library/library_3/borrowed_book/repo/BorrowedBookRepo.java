package io.library.library_3.borrowed_book.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.borrowed_book.entity.BorrowedBook;

public interface BorrowedBookRepo extends JpaRepository<BorrowedBook, Integer> {

}
