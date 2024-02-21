package io.library.library_3.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.student.StudentExceptionMessages;
import io.library.library_3.student.controller.StudentController;
import io.library.library_3.student.dtos.StudentReadingDTO;
import io.library.library_3.student.dtos.StudentUpdateDTO;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.mapper.StudentMapper;
import io.library.library_3.student.service.StudentService;

@ActiveProfiles("test")
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StudentService studentService;

        @MockBean
        private StudentMapper studentMapper;

        ObjectMapper mapper = new ObjectMapper();

        private static StudentUpdateDTO invalidCreationDTO;

        private static Student student;

        private static StudentUpdateDTO creationDTO;
        private static StudentReadingDTO readingDTO;

        private static List<Student> students;
        private static List<StudentReadingDTO> studentsDTOS;

        @BeforeAll
        public static void setUp() {
                invalidCreationDTO = new StudentUpdateDTO();
                invalidCreationDTO.setName("");
                invalidCreationDTO.setAddress("");
                invalidCreationDTO.setCollege("");

                student = new Student("Inmo Bob", "Arts", "Selly Oak");
                student.setId(100);
                students = new ArrayList<Student>();
                students.add(student);

                creationDTO = new StudentUpdateDTO();
                creationDTO.setName(student.getName());
                creationDTO.setAddress(student.getAddress());
                creationDTO.setCollege(student.getCollege());

                readingDTO = new StudentReadingDTO(student.getId(), student.getName(), student.getAddress(),
                                student.getCollege(), student.isRegistered());
                studentsDTOS = new ArrayList<StudentReadingDTO>();
                studentsDTOS.add(readingDTO);
        }

        @BeforeEach
        public void setUpMocks() {
                student.setId(100);
                when(studentMapper.toStudent(any(StudentUpdateDTO.class))).thenReturn(student);
                when(studentMapper.toReadingDTO(student)).thenReturn(readingDTO);
                when(studentMapper.toReadingDTO(students)).thenReturn(studentsDTOS);
        }

        @Test
        public void signUpStudent_Valid() throws Exception {
                when(studentService.signUpStudent(student)).thenReturn(student);

                mockMvc.perform(MockMvcRequestBuilders.post("/students")
                                .content(mapper.writeValueAsString(creationDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void signUpStudent_Invalid() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/students")
                                .content(mapper.writeValueAsString(invalidCreationDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void getStudents() throws Exception {
                when(studentService.getStudents()).thenReturn(students);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}]"));
        }

        @Test
        public void getStudents_Empty() throws Exception {
                when(studentService.getStudents()).thenReturn(new ArrayList<Student>());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[]"));
        }

        @Test
        public void getStudentsByName() throws Exception {
                when(studentService.getStudentsByName('"' + student.getName() + '"')).thenReturn(students);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byName")
                                                .content(mapper.writeValueAsString(student.getName()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}]"));
        }

        @Test
        public void getStudentsByName_Empty() throws Exception {
                when(studentService.getStudentsByName('"' + student.getName() + '"'))
                                .thenReturn(new ArrayList<Student>());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byName")
                                                .content(mapper.writeValueAsString(student.getName()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[]"));
        }

        @Test
        public void getStudentsByAddress() throws Exception {
                when(studentService.getStudentsByAdress('"' + student.getAddress() + '"')).thenReturn(students);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byAddress")
                                                .content(mapper.writeValueAsString(student.getAddress()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}]"));
        }

        @Test
        public void getStudentsByAddress_Empty() throws Exception {
                when(studentService.getStudentsByAdress('"' + student.getAddress() + '"'))
                                .thenReturn(new ArrayList<Student>());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byAddress")
                                                .content(mapper.writeValueAsString(student.getAddress()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[]"));
        }

        @Test
        public void getStudentsByCollege() throws Exception {
                when(studentService.getStudentsByCollege('"' + student.getCollege() + '"')).thenReturn(students);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byCollege")
                                                .content(mapper.writeValueAsString(student.getCollege()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}]"));
        }

        @Test
        public void getStudentsByCollege_Empty() throws Exception {
                when(studentService.getStudentsByCollege('"' + student.getCollege() + '"'))
                                .thenReturn(new ArrayList<Student>());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/byCollege")
                                                .content(mapper.writeValueAsString(student.getCollege()))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[]"));
        }

        @Test
        public void getRegisteredStudents() throws Exception {
                when(studentService.getRegisteredStudents()).thenReturn(students);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/registered"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}]"));
        }

        @Test
        public void getRegisteredStudents_Empty() throws Exception {
                when(studentService.getRegisteredStudents()).thenReturn(new ArrayList<Student>());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/registered"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[]"));
        }

        @Test
        public void getStudent_Existant() throws Exception {
                when(studentService.getStudent(student.getId())).thenReturn(student);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/" + (student.getId())))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void getStudent_NonExistant() throws Exception {
                doAnswer((i) -> {
                        throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1));
                }).when(studentService).getStudent(student.getId() - 1);

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/students/" + (student.getId() - 1)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void updateStudent_Existant() throws Exception {
                when(studentService.updateStudent(student)).thenReturn(student);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/students/" + (student.getId()))
                                                .content(mapper.writeValueAsString(creationDTO))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(student.getId()))
                                .andExpect(jsonPath("$.name").value(student.getName()));
        }

        @Test
        public void updateStudent_Invalid() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/students/" + (student.getId()))
                                                .content(mapper.writeValueAsString(invalidCreationDTO))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void updateStudent_NonExistant() throws Exception {
                student.setId(student.getId() - 1);
                doAnswer((i) -> {
                        throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1));
                }).when(studentService).updateStudent(student);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/students/" + (student.getId() - 1))
                                                .content(mapper.writeValueAsString(creationDTO))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void approveStudent_Existant() throws Exception {
                when(studentService.approveStudent(student.getId())).thenReturn(student);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/students/approve/" + (student.getId())))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void approveStudent_NonExistant() throws Exception {
                doAnswer((i) -> {
                        throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1));
                }).when(studentService).approveStudent(student.getId() - 1);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/students/approve/" + (student.getId() - 1)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().json("{}"));
        }

        @Test
        public void removeStudent_Existant() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/students/" + (student.getId())))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{}"));

        }

        @Test
        public void removeStudent_NonExistant() throws Exception {
                doAnswer((i) -> {
                        throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(student.getId() - 1));
                }).when(studentService).removeStudent(student.getId() - 1);

                mockMvc.perform(MockMvcRequestBuilders.delete("/students/" + (student.getId() - 1)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().json("{}"));
        }
}
