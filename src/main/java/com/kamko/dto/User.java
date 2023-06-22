package com.kamko.dto;

import lombok.*;

@Value(staticConstructor = "of")
public class User {
    String name;
    String password;
}
