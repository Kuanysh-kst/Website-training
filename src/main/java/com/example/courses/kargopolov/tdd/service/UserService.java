package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.FakeUser;

public interface UserService {
    FakeUser createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
