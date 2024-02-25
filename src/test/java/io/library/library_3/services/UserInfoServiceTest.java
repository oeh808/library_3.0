package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.auth.repo.UserInfoRepo;
import io.library.library_3.auth.service.UserInfoService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserInfoServiceTest {
    @TestConfiguration
    static class UserInfoServiceConfig {
        @Bean
        @Autowired
        UserInfoService userInfoService() {
            return new UserInfoService();
        }
    }

    @MockBean
    private UserInfoRepo userInfoRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoService userInfoService;

    private static UserInfo user;

    @BeforeAll
    public static void setUp() {
        user = new UserInfo(1, "username", "123456", "ROLE_USER");
    }

    @AfterEach
    public void tearDownForEach() {
        user.setPassword("123456");
    }

    @Test
    public void loadUserByUsername_Existant() {
        when(userInfoRepo.findByUsername("username")).thenReturn(Optional.of(user));

        assertEquals("username", userInfoService.loadUserByUsername("username").getUsername());
    }

    @Test
    public void loadUserByUsername_NonExistant() {
        when(userInfoRepo.findByUsername("Blah")).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> {
                    userInfoService.loadUserByUsername("Blah");
                });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    public void addUser() {
        when(passwordEncoder.encode("123456")).thenReturn("encodedPass");

        assertTrue(userInfoService.addUser(user).contains("User Added Successfully"));
        assertEquals("encodedPass", user.getPassword());
        verify(userInfoRepo, times(1)).save(any(UserInfo.class));
    }
}
