package io.library.library_3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.librarian.controller.LibrarianController;
import io.library.library_3.librarian.service.LibrarianService;

@WebMvcTest(LibrarianController.class)
public class LibrarianControllerTest {
    // TODO: Complete Controller Unit Test

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibrarianService librarianService;

    ObjectMapper mapper = new ObjectMapper();
}
