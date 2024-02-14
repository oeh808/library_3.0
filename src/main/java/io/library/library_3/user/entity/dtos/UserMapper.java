package io.library.library_3.user.entity.dtos;

import io.library.library_3.user.entity.User;

public class UserMapper {
    // To DTO
    public UserReadingDTO toDTO(User user) {
        UserReadingDTO dto = new UserReadingDTO(user.getId(), user.getName());

        return dto;
    }
}
