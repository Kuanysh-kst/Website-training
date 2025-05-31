package com.example.courses.kargopolov.repository;

import com.example.courses.kargopolov.model.FakeUser;

import java.util.HashMap;
import java.util.Map;

public class FakerUserRepositoryImpl implements FakerUserRepository {
    Map<String, FakeUser> users = new HashMap<>();
    @Override
    public boolean save(FakeUser user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }
}
