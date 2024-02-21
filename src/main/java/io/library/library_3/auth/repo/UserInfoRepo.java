package io.library.library_3.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.library.library_3.auth.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
}