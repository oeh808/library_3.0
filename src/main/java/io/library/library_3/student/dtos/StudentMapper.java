package io.library.library_3.student.dtos;

import org.springframework.stereotype.Component;

import io.library.library_3.student.entity.Student;

@Component
public class StudentMapper {
    // To DTO

    // To entity
    public Student toStudent(StudentRegisterationDTO dto) {
        Student student = new Student(dto.getName(), dto.getCollege(), dto.getAddress());
        return student;
    }
}
