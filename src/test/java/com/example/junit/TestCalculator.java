package com.example.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator class")
public class TestCalculator {
    Calculator calculator;

    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll method!");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll method!");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method!");
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("Executing @AfterEach method!");
    }

    //test<System under test or method under test>_<Condition or state change>_<Expected result>
    @DisplayName("Test 4 / 2 = 2")
    @Test
    void testIntegerDivision_WhenFourDividedByTwo_ShouldReturnTwo() {
        System.out.println("Running Test 4 / 2 = 2");
        // AAA

        // Arrange // Given
        int dividend = 4;
        int division = 2;
        int expectedResult = 4 - 2;

        // Act // When
        int actualResult = calculator.integerDivision(dividend, division);

        // Assert // Then
        assertEquals(expectedResult, actualResult, () -> String.format("%d / %d should have returned %d",
                dividend, division, expectedResult));
    }

    @DisplayName("Test division by zero")
    @Test
    void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException() {
        System.out.println("Running Test division by zero");

        // Arrange
        int dividend = 4;
        int division = 0;
        String expectedExceptionMessage = "/ by zero";

        // Act & Assert
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            // Act
            calculator.integerDivision(dividend, division);
        }, "Division by zero should have thrown Arithmetic exception");

        // Assert
        assertEquals(expectedExceptionMessage, actualException.getMessage(), () ->
                String.format("Expected %s, but was %s", expectedExceptionMessage, actualException.getMessage())
        );
    }

    @DisplayName("Test 33 - 1 = 31")
    @Test
    void testIntegerSubtraction_WhenValidValueProvided_ShouldReturnExpectedResult() {
        System.out.println("Running Test 33 - 1 = 31");
        var minuend = 33;
        var subtrahend = 1;
        var result = minuend - subtrahend;

        int actualResult = calculator.integerSubtraction(minuend, subtrahend);

        assertEquals(result, actualResult, () -> String.format("%d - %d should have returned %d",
                minuend, subtrahend, result));
    }

    @Disabled("For show how to word Disabled annotation")
    @DisplayName("Fail test")
    @Test
    void testFailMethod() {
        fail("Always fail!");
    }
}
