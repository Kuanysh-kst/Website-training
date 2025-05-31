package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.model.FakeUser;
import com.example.courses.kargopolov.repository.FakerUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    FakerUserServiceImpl userService;

    @Mock
    FakerUserRepository userRepository;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Test";
        lastName = "Test";
        email = "test@gmail.com";
        password = "12345";
        repeatPassword = "12345";
    }

    @Test
    void testCreateUser_WhenUserDetailsProvided_returnsUserObject() {
        // Arrange
        Mockito.when(userRepository.save(Mockito.any(FakeUser.class))).thenReturn(true);
        // Act
        FakeUser user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        Assertions.assertEquals(firstName, user.getFirstName(), "The firstName should be the same");
        Assertions.assertNotNull(user, "The createUser() should not have return null");
        Assertions.assertNotNull(user.getId(), "User missing the id");
    }

    @DisplayName("Empty first name causes  correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwIllegalArgumentException() {
        //Arrange
        firstName = "";
        // Act & Assert
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FakeUser user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");
        String expectedExceptionMessage = "User first name is empty";
        // Assert
        Assertions.assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(),
                "Exception error massage is not correct");
    }
}
