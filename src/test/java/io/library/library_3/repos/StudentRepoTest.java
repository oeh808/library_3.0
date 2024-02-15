package io.library.library_3.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;

@DataJpaTest
public class StudentRepoTest {
    @Autowired
    private StudentRepo studentRepo;

    private static Student student1;
    private static Student student2;

    @BeforeAll
    public static void setUp() {

        student1 = new Student("Inmo Bob", "Arts", "Selly Oak");
        student2 = new Student("Joe Shmoe", "Arts", "Hubert Rd");
    }

    @BeforeEach
    public void setUpForEach() {
        studentRepo.save(student1);
        studentRepo.save(student2);
    }

    @AfterEach
    public void tearDownForEach() {
        studentRepo.deleteAll();
    }

    @Test
    public void findByName_ReturnsSingletonList() {
        List<Student> res = studentRepo.findByNameContainingIgnoreCase("InMo Bob");

        assertTrue(res.size() == 1);
        assertEquals(res.get(0).getName(), student1.getName());
    }

    @Test
    public void findByName_ReturnsEmptyList() {
        List<Student> res = studentRepo.findByNameContainingIgnoreCase("InMoo Bob");

        assertTrue(res.isEmpty());
    }

    @Test
    public void findByCollege_ReturnsPopulatedList() {
        List<Student> res = studentRepo.findByCollegeIgnoreCase("ArTs");

        assertTrue(res.size() == 2);
        assertNotEquals(res.get(0), res.get(1));
    }

    @Test
    public void findByCollege_ReturnsEmptyList() {
        List<Student> res = studentRepo.findByCollegeIgnoreCase("ArTss");

        assertTrue(res.isEmpty());
    }

    @Test
    public void findByAddress_ReturnsSingletonList() {
        List<Student> res = studentRepo.findByAddressIgnoreCase("Selly Oak");

        assertTrue(res.size() == 1);
    }

    @Test
    public void findByAddress_ReturnsEmptyList() {
        List<Student> res = studentRepo.findByAddressIgnoreCase("Selly Oaks");

        assertTrue(res.isEmpty());
    }

    @Test
    public void findByRegistered_ReturnsPopulatedList() {
        List<Student> res = studentRepo.findByRegistered(false);

        assertTrue(res.size() == 2);
        assertNotEquals(res.get(0), res.get(1));
    }

    @Test
    public void findByRegistered_ReturnsEmptyList() {
        List<Student> res = studentRepo.findByRegistered(true);

        assertTrue(res.isEmpty());
    }

}
