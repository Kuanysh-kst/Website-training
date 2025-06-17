package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.TestingUser;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(TestingUser user) {
        System.out.println("scheduleEmailConfirmation is called");
    }
}
