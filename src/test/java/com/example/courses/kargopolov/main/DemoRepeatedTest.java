package com.example.courses.kargopolov.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Order(1)
public class DemoRepeatedTest {
    Calculator calculator;

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method!");
    }

    @DisplayName("Division by zero")
    @RepeatedTest(value = 3, name = "{displayName}. Repetition {currentRepetition} of {totalRepetitions}")
    void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException(
            RepetitionInfo repetitionInfo, TestInfo testInfo) {
        if (testInfo.getTestMethod().isPresent()) {
            System.out.printf("Running method: %s%n", testInfo.getTestMethod().get().getName());
        }

        System.out.printf("Division by zero! № %d of %d%n",
                repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions());
        var dividend = 3;
        var division = 0;
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
}
