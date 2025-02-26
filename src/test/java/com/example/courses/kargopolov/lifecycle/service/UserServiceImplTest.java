package com.example.courses.kargopolov.lifecycle.service;

import com.example.courses.kargopolov.lifecycle.io.UsersDatabase;
import com.example.courses.kargopolov.lifecycle.io.UsersDatabaseMapImpl;
import com.example.courses.kargopolov.lifecycle.service.UserService;
import com.example.courses.kargopolov.lifecycle.service.UserServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();

        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        // Arrange
        Map<String, String> user =  new HashMap<>();

        user.put("firstName", "John");
        user.put("lastName", "Wick");

        // Act
        createdUserId = userService.createUser(user);

        // Assert
        assertNotNull(createdUserId, "User id should not be null");
    }

    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        // Arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "John");
        newUserDetails.put("lastName", "Wick");

        // Act
        Map<String, String> updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        // Assert
        assertNotNull(updatedUserDetails, "updated user should not be null");
        assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName"),
                "Returned  value of user's first name is incorrect");
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        // Act
        Map<String, String> userDetails = userService.getUserDetails(createdUserId);

        // Assert
        assertNotNull(userDetails , "User details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"),
                "Returned user details contains incorrect user id");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        // Act
        userService.deleteUser(createdUserId);

        // Assert
        assertNull(usersDatabase.find(createdUserId), "User details should be null");
    }
}
