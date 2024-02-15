package io.library.library_3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.book.controller.BookController;
import io.library.library_3.book.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    // TODO: Complete Controller Unit Test

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    ObjectMapper mapper = new ObjectMapper();
}
