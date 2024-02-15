package io.library.library_3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.borrowed_book.controller.BorrowedBookController;
import io.library.library_3.borrowed_book.service.BorrowedBookService;

@WebMvcTest(BorrowedBookController.class)
public class BorrowedBookControllerTest {
    // TODO: Complete Controller Unit Test

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowedBookService borrowedBookService;

    ObjectMapper mapper = new ObjectMapper();
}
