package com.kamko;

import com.kamko.dto.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public Optional<User> login(String name, String password) {
        if (name == null || password == null) {
            throw new IllegalArgumentException();
        }
        return users.stream()
                .filter(el -> el.getName().equals(name))
                .filter(el -> el.getPassword().equals(password))
                .findFirst();
    }

    public Map<Integer, User> convertToMapById() {
        return users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
