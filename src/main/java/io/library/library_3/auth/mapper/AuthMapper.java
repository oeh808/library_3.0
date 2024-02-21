package io.library.library_3.auth.mapper;

import org.springframework.stereotype.Component;

import io.library.library_3.auth.dtos.LibrarianCreationDTO;
import io.library.library_3.auth.dtos.StudentCreationDTO;
import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.student.entity.Student;

@Component
public class AuthMapper {
    // To Entity
    public UserInfo toUserInfo(StudentCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles("ROLE_STUDENT");

        return user;
    }

    public UserInfo toUserInfo(LibrarianCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles("ROLE_LIBRARIAN,ROLE_STUDENT");

        return user;
    }

    public Student toStudent(StudentCreationDTO dto) {
        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setName(dto.getName());
        student.setCollege(dto.getCollege());
        student.setAddress(dto.getAddress());

        return student;
    }

    public Librarian toLibrarian(LibrarianCreationDTO dto) {
        Librarian librarian = new Librarian();
        librarian.setUsername(dto.getUsername());
        librarian.setName(dto.getName());
        librarian.setYearsOfExperience(dto.getYearsOfExperience());

        return librarian;
    }
}
