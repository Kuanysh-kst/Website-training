package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.TestingUser;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(TestingUser user);
}
