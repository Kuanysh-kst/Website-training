package com.example.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test Math operations in Calculator class")
public class TestCalculator {
    //test<System under test or method under test>_<Condition or state change>_<Expected result>
    @DisplayName("Test 4 / 2 = 2")
    @Test
    void testIntegerDivision_WhenFourDividedByTwo_ShouldReturnTwo() {
        //Arrange
        Calculator calculator = new Calculator();
        //Act
        int result = calculator.integerDivision(4, 2);
        //Assert
        assertEquals(2, result, "4/2 should have returned 2");
    }

    @DisplayName("Division by zero")
    @Test
    void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException() {
        Calculator calculator = new Calculator();

        int divedent = 4;
        int division = 0;
    }

    @DisplayName("33 - 1 = 31")
    @Test
    void testIntegerSubtraction_WhenValidValueProvided_ShouldReturnExpectedResult() {
        Calculator calculator = new Calculator();

        var minuend = 33;
        var subtrahend = 1;
        var result = minuend - subtrahend;

        int actualResult = calculator.integerSubtraction(minuend, subtrahend);

        assertEquals(result, actualResult, () -> String.format("%d - %d should have returned %d",
                minuend, subtrahend, result));
    }
}
