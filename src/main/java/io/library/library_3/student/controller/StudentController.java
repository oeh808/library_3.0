package io.library.library_3.student.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.custom_messages.CustomMessages;
import io.library.library_3.custom_messages.SuccessResponse;
import io.library.library_3.student.dtos.StudentReadingDTO;
import io.library.library_3.student.dtos.StudentUpdateDTO;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.mapper.StudentMapper;
import io.library.library_3.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
    // @Operation(description = "POST endpoint for signing up a student.", summary =
    // "Sign up a student")
    // @PostMapping()
    // public StudentReadingDTO signUpStudent(
    // @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description =
    // "Must conform to required properties of StudentRegisterationDTO")
    // @RequestBody StudentRegisterationDTO dto) {
    // return
    // studentMapper.toReadingDTO(studentService.signUpStudent(studentMapper.toStudent(dto)));
    // }

    // Read
    @Operation(description = "GET endpoint for retrieving a list of students.", summary = "Get all students")
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<StudentReadingDTO> getStudents() {
        return studentMapper.toReadingDTO(studentService.getStudents());
    }

    @Operation(description = "GET endpoint for retrieving a list of students with the same name.", summary = "Get all students by name")
    @GetMapping("/byName")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<StudentReadingDTO> getStudentsByName(@Parameter(name = "name") @RequestParam String name) {
        return studentMapper.toReadingDTO(studentService.getStudentsByName(name));
    }

    @Operation(description = "GET endpoint for retrieving a list of students with the same address.", summary = "Get all students by address")
    @GetMapping("/byAddress")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<StudentReadingDTO> getStudentsByAdress(
            @Parameter(name = "address") @RequestParam String address) {
        return studentMapper.toReadingDTO(studentService.getStudentsByAdress(address));
    }

    @Operation(description = "GET endpoint for retrieving a list of students with the same college.", summary = "Get all students by college")
    @GetMapping("/byCollege")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<StudentReadingDTO> getStudentsByCollege(
            @Parameter(name = "college") @RequestParam String college) {
        return studentMapper.toReadingDTO(studentService.getStudentsByCollege(college));
    }

    @Operation(description = "GET endpoint for retrieving a list of registered students.", summary = "Get all registered students")
    @GetMapping("/registered")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public List<StudentReadingDTO> getRegisteredStudents() {
        return studentMapper.toReadingDTO(studentService.getRegisteredStudents());
    }

    @Operation(description = "GET endpoint for retrieving a single student by their id.", summary = "Get student by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public StudentReadingDTO getStudent(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Student ID") @PathVariable int id) {
        return studentMapper.toReadingDTO(studentService.getStudent(id));
    }

    // Update
    @Operation(description = "PUT endpoint for updating a single student by their id.", summary = "Update student")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public StudentReadingDTO updateStudent(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Student ID") @PathVariable int id,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StudentUpdateDTO") @RequestBody StudentUpdateDTO dto) {
        Student student = studentMapper.toStudent(dto);
        student.setId(id);
        return studentMapper.toReadingDTO(studentService.updateStudent(student));
    }

    @Operation(description = "PUT endpoint for approving a student and setting them as registered.", summary = "Approve student")
    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public StudentReadingDTO approveStudent(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Student ID") @PathVariable int id) {
        return studentMapper.toReadingDTO(studentService.approveStudent(id));
    }

    // Delete
    @Operation(description = "DELETE endpoint for deleting a student by their id.", summary = "Delete student")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public SuccessResponse removeStudent(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Student ID") @PathVariable int id) {
        studentService.removeStudent(id);

        return new SuccessResponse(CustomMessages.DELETE_IS_SUCCESSFUL);
    }
}
