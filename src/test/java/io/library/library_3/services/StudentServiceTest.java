package io.library.library_3.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.student.repo.StudentRepo;
import io.library.library_3.student.service.StudentService;
import io.library.library_3.student.service.StudentServiceImpl;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    // TODO: Complete unit test

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

    @BeforeAll
    public static void setUp() {

    }
}
