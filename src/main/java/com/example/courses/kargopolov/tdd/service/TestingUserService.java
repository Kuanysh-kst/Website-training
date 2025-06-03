package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.TestingUser;

public interface TestingUserService {
    TestingUser createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
