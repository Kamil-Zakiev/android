package com.example.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorUnitTest {
    @Test
    public void TestAddition(){
        CalculatorImpl calculator = new CalculatorImpl();
        assertEquals("5", calculator.Calculate("2", "+", "3"));
        assertEquals("5", calculator.Calculate("3", "+", "2"));
    }

    @Test
    public void TestSubtraction(){
        CalculatorImpl calculator = new CalculatorImpl();
        assertEquals("-1", calculator.Calculate("2", "-", "3"));
        assertEquals("1", calculator.Calculate("3", "-", "2"));
    }

    @Test
    public void TestExprAddition(){
        CalculatorImpl calculator = new CalculatorImpl();
        assertEquals("5", calculator.Calculate("2 + 3", " "));
        assertEquals("5", calculator.Calculate("3 + 2", " "));
    }

    @Test
    public void TestExprSubtraction(){
        CalculatorImpl calculator = new CalculatorImpl();
        assertEquals("-1", calculator.Calculate("2 - 3", " "));
        assertEquals("1", calculator.Calculate("3 - 2", " "));
    }
}