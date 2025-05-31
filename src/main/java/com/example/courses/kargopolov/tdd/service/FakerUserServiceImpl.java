package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.FakeUser;
import com.example.courses.kargopolov.repository.FakerUserRepository;
import com.example.courses.kargopolov.exception.FakerUserException;

import java.util.UUID;

public class FakerUserServiceImpl implements UserService {

    FakerUserRepository userRepository;

    public FakerUserServiceImpl(FakerUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FakeUser createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("User first name is empty");
        }

        FakeUser fakeUser = new FakeUser(UUID.randomUUID().toString(), firstName, lastName, email, password, repeatPassword);

        boolean isUserCreated = userRepository.save(fakeUser);

        if (!isUserCreated) {
            throw new FakerUserException("Could not create user");
        }
        return fakeUser;
    }
}
