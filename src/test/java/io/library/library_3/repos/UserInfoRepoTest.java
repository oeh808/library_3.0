package io.library.library_3.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.auth.repo.UserInfoRepo;

@ActiveProfiles("test")
@DataJpaTest
public class UserInfoRepoTest {

    @Autowired
    private UserInfoRepo userInfoRepo;

    private static UserInfo user;

    @BeforeAll
    public static void setUp() {
        user = new UserInfo(100, "GingerBeard", "password", "ROLE_STUDENT");
    }

    @BeforeEach
    public void setUpForEach() {
        userInfoRepo.save(user);
    }

    @AfterEach
    public void tearDownForEach() {
        userInfoRepo.deleteAll();
    }

    @Test
    public void findByUserName_Existant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUsername(user.getUsername());

        assertTrue(opUser.isPresent());
        assertEquals(user.getUsername(), opUser.get().getUsername());
    }

    @Test
    public void findByUserName_NonExistant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUsername("Blah");

        assertTrue(opUser.isEmpty());
    }
}
