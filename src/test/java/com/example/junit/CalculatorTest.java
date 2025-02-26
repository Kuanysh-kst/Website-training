package com.example.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator class")
public class CalculatorTest {
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
    @DisplayName("Test integer division [dividend, division, expectedResult]")
    @ParameterizedTest
    @MethodSource()
    void testIntegerDivision_WhenFourDividedByTwo_ShouldReturnTwo(int dividend, int division, int expectedResult) {
        System.out.printf("Running Test %d / %d = %d%n", dividend, division, expectedResult);

        // Act // When
        int actualResult = calculator.integerDivision(dividend, division);

        // Assert // Then
        assertEquals(expectedResult, actualResult, () -> String.format("%d / %d should have returned %d",
                dividend, division, expectedResult));
    }

    @DisplayName("Test integer division by zero [dividend, division]")
    @ParameterizedTest
    @MethodSource
    void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException(int dividend, int division) {
        System.out.printf("Running Test %d by zero%n", dividend);

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

    @DisplayName("Test integer subtraction [minuend, subtrahend, expectedResult]")
    @ParameterizedTest
    @MethodSource()
    void testIntegerSubtraction_WhenValidValueProvided_ShouldReturnExpectedResult(int minuend, int subtrahend, int expectedResult) {
        System.out.printf("Running Test %d - %d = %d%n", minuend, subtrahend, expectedResult);

        int actualResult = calculator.integerSubtraction(minuend, subtrahend);

        assertEquals(expectedResult, actualResult, () -> String.format("%d - %d should have returned %d",
                minuend, subtrahend, expectedResult));
    }

    @Disabled("For show how to word Disabled annotation")
    @DisplayName("Fail test")
    @Test
    void testFailMethod() {
        fail("Always fail!");
    }

    @DisplayName("Test integer multiply [i, j]")
    @ParameterizedTest
    @CsvFileSource(resources = "/integerMultiply.csv")
    void integerMultiply(int i, int j, int expectedResult) {
        System.out.printf("Running Test %d * %d = %d%n", i, j, expectedResult);

        int actualResult = calculator.integerMultiply(i, j);

        assertEquals(expectedResult, actualResult,() ->  String.format("%d * %d should have returned %d, but was %d",
                i, j, expectedResult, actualResult));
    }

    private static Stream<Arguments> testIntegerSubtraction_WhenValidValueProvided_ShouldReturnExpectedResult() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(43, 1, 42)
        );
    }

    private static Stream<Arguments> testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException() {
        return Stream.of(
                Arguments.of(4, 0),
                Arguments.of(5, 0)
        );
    }

    private static Stream<Arguments> testIntegerDivision_WhenFourDividedByTwo_ShouldReturnTwo() {
        return Stream.of(
                Arguments.of(4, 2, 2),
                Arguments.of(100, 20, 5)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Kate", "Alice"})
    void valueSourceDemonstration(String firstName) {
        System.out.println(firstName);
        assertNotNull(firstName, "Should be string value, but was null");
    }
}
