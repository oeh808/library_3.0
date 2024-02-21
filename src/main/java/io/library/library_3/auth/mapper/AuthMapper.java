package io.library.library_3.auth.mapper;

import org.springframework.stereotype.Component;

import io.library.library_3.auth.dtos.LibrarianCreationDTO;
import io.library.library_3.auth.dtos.StudentCreationDTO;
import io.library.library_3.auth.entity.UserInfo;

@Component
public class AuthMapper {
    // To DTO

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
        user.setRoles("ROLE_STUDENT, ROLE_LIBRARIAN");
        return user;
    }
}
