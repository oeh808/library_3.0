package io.library.library_3.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.librarian.LibrarianExceptionMessages;
import io.library.library_3.librarian.controller.LibrarianController;
import io.library.library_3.librarian.dtos.LibrarianUpdateDTO;
import io.library.library_3.librarian.dtos.LibrarianReadingDTO;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.mapper.LibrarianMapper;
import io.library.library_3.librarian.service.LibrarianService;

@ActiveProfiles("test")
@WebMvcTest(LibrarianController.class)
// FIXME: Update tests
public class LibrarianControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibrarianService librarianService;

    @MockBean
    private LibrarianMapper librarianMapper;

    ObjectMapper mapper = new ObjectMapper();

    private static LibrarianUpdateDTO invalidCreationDTO;

    private static Librarian librarian;
    private static LibrarianUpdateDTO creationDTO;
    private static LibrarianReadingDTO readingDTO;

    @BeforeAll
    public static void setUp() {
        invalidCreationDTO = new LibrarianUpdateDTO();
        invalidCreationDTO.setName("");
        invalidCreationDTO.setYearsOfExperience(-1);

        librarian = new Librarian(null, "Craig Robinson", 3);
        librarian.setId(100);

        creationDTO = new LibrarianUpdateDTO();
        creationDTO.setName(librarian.getName());
        creationDTO.setYearsOfExperience(librarian.getYearsOfExperience());

        readingDTO = new LibrarianReadingDTO(librarian.getId(), librarian.getName(), librarian.getYearsOfExperience());
    }

    @BeforeEach
    public void setUpMocks() {
        librarian.setId(100);
        when(librarianMapper.toLibrarian(any(LibrarianUpdateDTO.class))).thenReturn(librarian);
        when(librarianMapper.toReadingDTO(librarian)).thenReturn(readingDTO);
    }

    @Test
    public void addLibrarian_Valid() throws Exception {
        when(librarianService.addLibrarian(librarian)).thenReturn(librarian);

        mockMvc.perform(MockMvcRequestBuilders.post("/librarians")
                .content(mapper.writeValueAsString(creationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void addLibrarian_Invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/librarians")
                .content(mapper.writeValueAsString(invalidCreationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getLibrarians() throws Exception {
        List<Librarian> librarians = new ArrayList<Librarian>();
        librarians.add(librarian);
        when(librarianService.getLibrarians()).thenReturn(librarians);
        List<LibrarianReadingDTO> librariansDTOS = new ArrayList<>();
        librariansDTOS.add(readingDTO);
        when(librarianMapper.toReadingDTO(librarians)).thenReturn(librariansDTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/librarians"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    public void getLibrarians_Empty() throws Exception {
        List<Librarian> librarians = new ArrayList<Librarian>();
        when(librarianService.getLibrarians()).thenReturn(librarians);
        List<LibrarianReadingDTO> librariansDTOS = new ArrayList<>();
        when(librarianMapper.toReadingDTO(librarians)).thenReturn(librariansDTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/librarians"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getLibrarian_Existant() throws Exception {
        when(librarianService.getLibrarian(librarian.getId())).thenReturn(librarian);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/librarians/" + (librarian.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void getLibrarian_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1));
        }).when(librarianService).getLibrarian(librarian.getId() - 1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/librarians/" + (librarian.getId() - 1)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateFollower_Existant() throws Exception {
        when(librarianService.updateLibrarian(librarian)).thenReturn(librarian);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/librarians/" + (librarian.getId()))
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(librarian.getId()))
                .andExpect(jsonPath("$.name").value(librarian.getName()));
    }

    @Test
    public void updateFollower_Invalid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/librarians/" + (librarian.getId()))
                        .content(mapper.writeValueAsString(invalidCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{}"));
    }

    @Test
    public void updateFollower_NonExistant() throws Exception {
        librarian.setId(librarian.getId() - 1);
        doAnswer((i) -> {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1));
        }).when(librarianService).updateLibrarian(librarian);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/librarians/" + (librarian.getId()))
                        .content(mapper.writeValueAsString(creationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }

    @Test
    public void deleteFollower_Existant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/librarians/" + (librarian.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void deleteFollower_NonExistant() throws Exception {
        doAnswer((i) -> {
            throw new EntityNotFoundException(LibrarianExceptionMessages.ID_NOT_FOUND(librarian.getId() - 1));
        }).when(librarianService).deleteLibrarian(librarian.getId() - 1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/librarians/" + (librarian.getId() - 1)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{}"));
    }
}
