package io.library.library_3.student.controller;

import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.student.dtos.StudentMapper;
import io.library.library_3.student.dtos.StudentRegisterationDTO;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.service.StudentService;
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
public class StudentController {
    private StudentService studentService;
    private StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    // Create
    @PostMapping()
    public Student signUpStudent(@Valid @RequestBody StudentRegisterationDTO dto) {
        return studentService.signUpStudent(studentMapper.toStudent(dto));
    }

    // Read
    @GetMapping()
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/byAddress")
    public List<Student> getStudentsByAdress(@RequestBody String address) {
        return studentService.getStudentsByAdress(address);
    }

    @GetMapping("/byCollege")
    public List<Student> getStudentsByCollege(@RequestBody String college) {
        return studentService.getStudentsByCollege(college);
    }

    @GetMapping("/registered")
    public List<Student> getRegisteredStudents() {
        return studentService.getRegisteredStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentService.getStudent(id);
    }

    // Update
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody StudentRegisterationDTO dto) {
        Student student = studentMapper.toStudent(dto);
        student.setId(id);
        return studentService.updateStudent(student);
    }

    @PutMapping("/approve/{id}")
    public Student approveStudent(@PathVariable int id) {
        return studentService.approveStudent(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public Student removeStudent(@PathVariable int id) {
        return studentService.removeStudent(id);
    }
}
