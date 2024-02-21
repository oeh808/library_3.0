package io.library.library_3.student.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.library.library_3.student.dtos.StudentReadingDTO;
import io.library.library_3.student.dtos.StudentUpdateDTO;
import io.library.library_3.student.entity.Student;

@Component
public class StudentMapper {
    // To DTO
    public StudentReadingDTO toReadingDTO(Student student) {
        StudentReadingDTO dto = new StudentReadingDTO(student.getId(), student.getName(),
                student.getAddress(), student.getCollege(), student.isRegistered());

        return dto;
    }

    public List<StudentReadingDTO> toReadingDTO(List<Student> students) {
        List<StudentReadingDTO> dtos = new ArrayList<StudentReadingDTO>();
        for (Student student : students) {
            dtos.add(toReadingDTO(student));
        }

        return dtos;
    }

    // To entity
    public Student toStudent(StudentUpdateDTO dto) {
        Student student = new Student(dto.getName(), dto.getCollege(), dto.getAddress());
        return student;
    }
}
