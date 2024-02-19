package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.student.StudentExceptionMessages;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;
import io.library.library_3.student.service.StudentService;
import io.library.library_3.student.service.StudentServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @TestConfiguration
    static class StudentServiceTestConfig {
        @Bean
        @Autowired
        StudentService studentService(StudentRepo studentRepo) {
            return new StudentServiceImpl(studentRepo);
        }
    }

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    StudentService studentService;

    private static Student student;

    @BeforeAll
    public static void setUp() {
        student = new Student("Inmo Bob", "Arts", "Selly Oak");
    }

    @BeforeEach
    public void setUpForEach() {
        student.setRegistered(false);
    }

    @Test
    public void signUpStudent() {
        when(studentRepo.save(student)).thenReturn(student);

        assertEquals(student, studentService.signUpStudent(student));
    }

    @Test
    public void getStudents() {
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        when(studentRepo.findAll()).thenReturn(students);

        assertEquals(students, studentService.getStudents());
    }

    @Test
    public void getStudentsByName_Existant() {
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        when(studentRepo.findByNameContainingIgnoreCase(student.getName())).thenReturn(students);

        assertEquals(students, studentService.getStudentsByName(student.getName()));
    }

    @Test
    public void getStudentsByName_NonExistant() {
        List<Student> students = new ArrayList<Student>();
        when(studentRepo.findByNameContainingIgnoreCase("Waaah")).thenReturn(students);

        assertTrue(studentService.getStudentsByName("Waaah").isEmpty());
    }

    @Test
    public void getStudentsByAdress_Existant() {
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        when(studentRepo.findByAddressIgnoreCase(student.getAddress())).thenReturn(students);

        assertEquals(students, studentService.getStudentsByAdress(student.getAddress()));
    }

    @Test
    public void getStudentsByAddress_NonExistant() {
        List<Student> students = new ArrayList<Student>();
        when(studentRepo.findByAddressIgnoreCase("Waaah")).thenReturn(students);

        assertTrue(studentService.getStudentsByAdress("Waaah").isEmpty());
    }

    @Test
    public void getStudentsByCollege_Existant() {
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        when(studentRepo.findByCollegeIgnoreCase(student.getCollege())).thenReturn(students);

        assertEquals(students, studentService.getStudentsByCollege(student.getCollege()));
    }

    @Test
    public void getStudentsByCollege_NonExistant() {
        List<Student> students = new ArrayList<Student>();
        when(studentRepo.findByCollegeIgnoreCase("Waaah")).thenReturn(students);

        assertTrue(studentService.getStudentsByCollege("Waaah").isEmpty());
    }

    @Test
    public void getRegisteredStudents_Existant() {
        student.setRegistered(true);
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        when(studentRepo.findByRegistered(student.isRegistered())).thenReturn(students);

        assertEquals(students, studentService.getRegisteredStudents());
    }

    @Test
    public void getRegisteredStudents_NonExistant() {
        List<Student> students = new ArrayList<Student>();
        when(studentRepo.findByRegistered(true)).thenReturn(students);

        assertTrue(studentService.getRegisteredStudents().isEmpty());
    }

    @Test
    public void getStudent_Existant() {
        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));

        assertEquals(student, studentService.getStudent(student.getId()));
    }

    @Test
    public void getStudent_NonExistant() {
        when(studentRepo.findById(student.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    studentService.getStudent(student.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1)));
    }

    @Test
    public void updateStudent_Existant() {
        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepo.save(student)).thenReturn(student);

        assertEquals(student, studentService.updateStudent(student));
    }

    @Test
    public void updateStudent_NonExistant() {
        when(studentRepo.findById(student.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    Student someStudent = new Student();
                    someStudent.setId(student.getId() - 1);
                    studentService.updateStudent(someStudent);
                });
        assertTrue(ex.getMessage().contains(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1)));
    }

    @Test
    public void deleteStudent_Existant() {
        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.removeStudent(student.getId());

        verify(studentRepo, times(1)).deleteById(student.getId());
    }

    @Test
    public void deleteStudent_NonExistant() {
        when(studentRepo.findById(student.getId() - 1)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> {
                    studentService.removeStudent(student.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1)));
    }
}
