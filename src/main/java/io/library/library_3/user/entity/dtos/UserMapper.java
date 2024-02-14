package io.library.library_3.user.entity.dtos;

import java.util.ArrayList;
import java.util.List;

import io.library.library_3.user.entity.User;

public class UserMapper {
    // To DTO
    public UserReadingDTO toDTO(User user) {
        UserReadingDTO dto = new UserReadingDTO(user.getId(), user.getName());

        return dto;
    }

    public List<UserReadingDTO> toDTO(List<User> users) {
        List<UserReadingDTO> dtos = new ArrayList<UserReadingDTO>();
        for (User user : users) {
            dtos.add(toDTO(user));
        }

        return dtos;
    }
}
