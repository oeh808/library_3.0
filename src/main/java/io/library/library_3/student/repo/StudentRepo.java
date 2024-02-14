package io.library.library_3.student.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.student.entity.Student;
import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    public List<Student> findByNameContainingIgnoreCase(String name);

    public List<Student> findByAddressIgnoreCase(String address);

    public List<Student> findByCollegeIgnoreCase(String college);

    public List<Student> findByRegistered(boolean registered);
}
