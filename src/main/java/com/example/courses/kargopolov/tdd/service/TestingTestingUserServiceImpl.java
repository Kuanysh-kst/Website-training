package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.TestingUser;
import com.example.courses.kargopolov.repository.TestingUserRepository;
import com.example.courses.kargopolov.exception.TestingUserException;

import java.util.UUID;

public class TestingTestingUserServiceImpl implements TestingUserService {

    TestingUserRepository userRepository;
    EmailVerificationService emailVerificationService;

    public TestingTestingUserServiceImpl(TestingUserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public TestingUser createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("User first name is empty");
        }

        TestingUser user = new TestingUser(UUID.randomUUID().toString(), firstName, lastName, email, password, repeatPassword);

        boolean isUserCreated;

        try {
            isUserCreated = userRepository.save(user);
        } catch (RuntimeException exception) {
            throw new TestingUserException(exception.getMessage());
        }

        if (!isUserCreated) {
            throw new TestingUserException("Could not create user");
        }

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException exception) {
            throw new TestingUserException(exception.getMessage());
        }
        return user;
    }
}
