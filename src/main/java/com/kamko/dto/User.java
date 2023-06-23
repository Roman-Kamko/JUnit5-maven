package com.kamko.dto;

import lombok.*;

@Value(staticConstructor = "of")
public class User {
    Integer id;
    String name;
    String password;
}
