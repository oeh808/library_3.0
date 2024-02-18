package io.library.library_3.student.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.student.dtos.StudentReadingDTO;
import io.library.library_3.student.dtos.StudentRegisterationDTO;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.mapper.StudentMapper;
import io.library.library_3.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("students")
@Tag(name = "Students")
public class StudentController {
    private StudentService studentService;
    private StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    // Create
    @Operation(description = "POST endpoint for signing up a student.", summary = "Sign up a student")
    @PostMapping()
    public StudentReadingDTO signUpStudent(@Valid @RequestBody StudentRegisterationDTO dto) {
        return studentMapper.toReadingDTO(studentService.signUpStudent(studentMapper.toStudent(dto)));
    }

    // Read
    @GetMapping()
    public List<StudentReadingDTO> getStudents() {
        return studentMapper.toReadingDTO(studentService.getStudents());
    }

    @GetMapping("/byName")
    public List<StudentReadingDTO> getStudentsByName(@RequestBody String name) {
        return studentMapper.toReadingDTO(studentService.getStudentsByName(name));
    }

    @GetMapping("/byAddress")
    public List<StudentReadingDTO> getStudentsByAdress(@RequestBody String address) {
        return studentMapper.toReadingDTO(studentService.getStudentsByAdress(address));
    }

    @GetMapping("/byCollege")
    public List<StudentReadingDTO> getStudentsByCollege(@RequestBody String college) {
        return studentMapper.toReadingDTO(studentService.getStudentsByCollege(college));
    }

    @GetMapping("/registered")
    public List<StudentReadingDTO> getRegisteredStudents() {
        return studentMapper.toReadingDTO(studentService.getRegisteredStudents());
    }

    @GetMapping("/{id}")
    public StudentReadingDTO getStudent(@PathVariable int id) {
        return studentMapper.toReadingDTO(studentService.getStudent(id));
    }

    // Update
    @PutMapping("/{id}")
    public StudentReadingDTO updateStudent(@PathVariable int id, @Valid @RequestBody StudentRegisterationDTO dto) {
        Student student = studentMapper.toStudent(dto);
        student.setId(id);
        return studentMapper.toReadingDTO(studentService.updateStudent(student));
    }

    @PutMapping("/approve/{id}")
    public StudentReadingDTO approveStudent(@PathVariable int id) {
        return studentMapper.toReadingDTO(studentService.approveStudent(id));
    }

    // Delete
    @DeleteMapping("/{id}")
    public SuccessResponse removeStudent(@PathVariable int id) {
        studentService.removeStudent(id);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }
}
