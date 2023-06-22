package com.kamko;

import com.kamko.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final User PETR = User.of("Petr", "123" );
    private static final User IVAN = User.of("Ivan", "qwe" );
    private UserService out;
    @BeforeEach
    void prepare() {
        out = new UserService();
        out.add(IVAN);
        out.add(PETR);
    }
    @Test
    void usersEmptyIfNoUserAdded() {
        assertFalse(out.getAll().isEmpty());
    }

    @Test
    void usersSizeIfUserAdded() {
        assertEquals(2, out.getAll().size());
    }

    @Test
    void loginSuccessIfUserExist() {
        Optional<User> maybeUser = out.login(IVAN.getName(), IVAN.getPassword());
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(IVAN, user));
    }

    @Test
    void loginFailIfUserNameWrong() {
        Optional<User> maybeUser = out.login("qwer", PETR.getPassword());
        assertFalse(maybeUser.isPresent());
    }

    @Test
    void loginFailIfUserPasswordWrong() {
        Optional<User> maybeUser = out.login(PETR.getName(), "qwe");
        assertFalse(maybeUser.isPresent());
    }
}