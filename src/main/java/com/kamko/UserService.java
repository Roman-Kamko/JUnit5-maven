package com.kamko;

import com.kamko.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public Optional<User> login(String name, String password) {
        return users.stream()
                .filter(el -> el.getName().equals(name))
                .filter(el -> el.getPassword().equals(password))
                .findFirst();
    }
}
