package com.example.courses.kargopolov.repository;

import com.example.courses.kargopolov.model.TestingUser;

import java.util.HashMap;
import java.util.Map;

public class TestingUserRepositoryImpl implements TestingUserRepository {
    Map<String, TestingUser> users = new HashMap<>();
    @Override
    public boolean save(TestingUser user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }
}
