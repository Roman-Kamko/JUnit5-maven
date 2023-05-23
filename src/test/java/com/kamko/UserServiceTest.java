package com.kamko;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    void test() {
        UserService userService = new UserService();
        userService.getAll();
    }
}