package io.library.library_3.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.library.library_3.auth.AuthExceptionMessages;
import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.auth.repo.UserInfoRepo;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepo repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo) {
        Optional<UserInfo> opUser = repository.findByUsername(userInfo.getUsername());

        if (opUser.isPresent()) {
            throw new DuplicateEntityException(AuthExceptionMessages.USERNAME_ALREADY_EXISTS(userInfo.getUsername()));
        }

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
}
