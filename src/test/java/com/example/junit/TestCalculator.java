package com.example.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCalculator {
    @Test
    void testIntegerDivision_whenValidValueProvided_shouldReturnExpectedResult() {
        //Arrange
        Calculator calculator = new Calculator();
        //Act
        int result = calculator.integerDivision(4, 2);
        //Assert
        assertEquals(2, result, "4/2 should have returned 2");
    }

    @Test
    void testIntegerSubtraction() {
        Calculator calculator = new Calculator();

        var minuend = 33;
        var subtrahend = 1;
        var result = minuend - subtrahend;

        int actualResult = calculator.integerSubtraction(minuend, subtrahend);

        assertEquals(result, actualResult, String.format("%d - %d should have returned %d",
                minuend, subtrahend, result));
    }
}
