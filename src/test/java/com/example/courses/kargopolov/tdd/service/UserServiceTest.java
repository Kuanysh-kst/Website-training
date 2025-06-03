package com.example.courses.kargopolov.tdd.service;

import com.example.courses.kargopolov.exception.EmailVerificationException;
import com.example.courses.kargopolov.exception.TestingUserException;
import com.example.courses.kargopolov.model.TestingUser;
import com.example.courses.kargopolov.repository.TestingUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    TestingTestingUserServiceImpl userService;

    @Mock
    TestingUserRepository userRepository;

    @Mock
    EmailVerificationService emailVerificationService;

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
        when(userRepository.save(any(TestingUser.class))).thenReturn(true);
        // Act
        TestingUser user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertEquals(firstName, user.getFirstName(), "The firstName should be the same");
        assertNotNull(user, "The createUser() should not have return null");
        assertNotNull(user.getId(), "User missing the id");
        verify(userRepository, times(1)).save(any(TestingUser.class));
    }

    @DisplayName("Empty first name causes  correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwIllegalArgumentException() {
        //Arrange
        firstName = "";
        // Act & Assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword), "Empty first name should have caused an Illegal Argument Exception");
        String expectedExceptionMessage = "User first name is empty";
        // Assert
        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(),
                "Exception error massage is not correct");
    }

    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        when(userRepository.save(any(TestingUser.class))).thenThrow(RuntimeException.class);
        // Act and Assert
        assertThrows(TestingUserException.class, () ->
                userService.createUser(firstName, lastName, email, password, repeatPassword), "Should have thrown FakerUserException instead");
    }

    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException() {
        //Arrange
        when(userRepository.save(any(TestingUser.class))).thenReturn(true);

        doThrow(EmailVerificationException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(any(TestingUser.class));

        assertThrows(TestingUserException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword)
                , "Should have thrown TestingUserException instead");

    }
}
