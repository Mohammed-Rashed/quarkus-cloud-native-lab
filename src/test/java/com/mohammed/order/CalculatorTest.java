package com.mohammed.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    @Test
    void shouldAddTwoNumbers() {

        // Arrange
        Calculator calculator = new Calculator();

        // Act
        int result = calculator.add(2, 3);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void shouldThrowExceptionWhenDividingByZero() {

        Calculator calculator = new Calculator();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.divide(10, 0)
                );

        assertEquals(
                "Cannot divide by zero",
                exception.getMessage()
        );
    }
}
