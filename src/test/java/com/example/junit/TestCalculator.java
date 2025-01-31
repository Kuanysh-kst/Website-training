package com.example.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCalculator {
    @Test
    void testIntegerDivision_whenValidValueProvided_shouldReturnExpectedResult() {
        //Arrange
        Calculator calculator = new Calculator();
        //Act
        int result = calculator.integerDivision(4, 2);
        //Assert
        Assertions.assertEquals(2, result, "4/2 should have returned 2");
    }
}
