package io.library.library_3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.student.controller.StudentController;
import io.library.library_3.student.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    // TODO: Complete Controller Unit Test

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    ObjectMapper mapper = new ObjectMapper();
}
