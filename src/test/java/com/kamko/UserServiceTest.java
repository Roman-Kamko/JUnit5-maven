package com.kamko;

import com.kamko.dto.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import paramresolver.UserServiceParamResolver;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
@Tag("userService")
@ExtendWith({
        UserServiceParamResolver.class
})
class UserServiceTest {

    private static final User PETR = User.of(1, "Petr", "123");
    private static final User IVAN = User.of(2, "Ivan", "qwe");
    private UserService out;

    @BeforeEach
    void prepare(UserService userService) {
        out = userService;
        out.add(IVAN);
        out.add(PETR);
    }

    @Test
    void usersConvertedToMapById() {
        Map<Integer, User> users = out.convertToMapById();
        assertAll(
                () -> assertThat(users).containsKeys(PETR.getId(), IVAN.getId()),
                () -> assertThat(users).containsValues(PETR, IVAN)
        );
    }
    @Test
    void usersEmptyIfNoUserAdded() {
        assertThat(out.getAll().isEmpty()).isFalse();
    }

    @Test
    void usersSizeIfUserAdded() {
        assertThat(out.getAll()).hasSize(2);
    }
    @Nested
    @Tag("login")
    @DisplayName("login functionality tests")
    class loginTest {
        @Test
        void loginSuccessIfUserExist() {
            Optional<User> maybeUser = out.login(IVAN.getName(), IVAN.getPassword());
            assertThat(maybeUser).isPresent();
            maybeUser.ifPresent(user -> assertThat(IVAN).isEqualTo(user));
        }

        @Test
        void loginFailIfUserNameWrong() {
            Optional<User> maybeUser = out.login("qwer", PETR.getPassword());
            assertThat(maybeUser).isEmpty();
        }

        @Test
        void loginFailIfUserPasswordWrong() {
            Optional<User> maybeUser = out.login(PETR.getName(), "qwe");
            assertThat(maybeUser).isEmpty();
        }

        @Test
        void loginThrowExceptionIfUserNameOrPasswordIsNull() {
            assertAll(
                    ()->assertThatExceptionOfType(IllegalArgumentException.class)
                            .isThrownBy(() -> out.login(null, "asd")),
                    ()->assertThatExceptionOfType(IllegalArgumentException.class)
                            .isThrownBy(() -> out.login("asd", null))
            );
        }
        @ParameterizedTest(name = "test [{index}]")
        @DisplayName("login param test")
        @MethodSource("com.kamko.UserServiceTest#paramByLoginParamTest")
        void loginParametrizedTest(String name, String password, Optional<User> user) {
            assertThat(out.login(name, password)).isEqualTo(user);
        }
    }

    static Stream<Arguments> paramByLoginParamTest() {
        return Stream.of(
                Arguments.of("Ivan", "qwe", Optional.of(IVAN)),
                Arguments.of("Petr", "123", Optional.of(PETR)),
                Arguments.of("asda", "qwe", Optional.empty()),
                Arguments.of("Ivan", "cas", Optional.empty())
        );
    }
}