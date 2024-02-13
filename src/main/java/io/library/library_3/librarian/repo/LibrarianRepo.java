package io.library.library_3.librarian.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.librarian.entity.Librarian;

public interface LibrarianRepo extends JpaRepository<Librarian, Integer> {

}
