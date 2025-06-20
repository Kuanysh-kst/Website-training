package com.example.courses.kargopolov.lifecycle.io;

import java.util.HashMap;
import java.util.Map;

public class UsersDatabaseMapImpl implements UsersDatabase {
    Map<String, Map<String, String>> users;

    @Override
    public void init() {
        users = new HashMap<>();
    }

    @Override
    public void close() {
        users = null;
    }

    @Override
    public Map<String, String> save(String userId, Map<String, String> userDetails) {
        return users.put(userId, userDetails);
    }

    @Override
    public Map<String, String> update(String userId, Map<String, String> user) {
        users.put(userId, user);
        return users.get(userId);
    }

    @Override
    public Map<String, String> find(String userId) {
        return users.get(userId);
    }

    @Override
    public void delete(String userId) {
        users.remove(userId);
    }
}
