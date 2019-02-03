package com.example.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionBuilderTests {
    @Test
    public void Test1() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('1');
        assertEquals("1", exprBuilder.GetExpression());

        exprBuilder.Append('+');
        assertEquals("1 +", exprBuilder.GetExpression());

        exprBuilder.Append('3');
        assertEquals("1 + 3", exprBuilder.GetExpression());

        exprBuilder.Append('-');
        assertEquals("4 -", exprBuilder.GetExpression());

        exprBuilder.Append('+');
        assertEquals("4 +", exprBuilder.GetExpression());
    }

    @Test
    public void Test2() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('-');
        exprBuilder.Append('+');
        exprBuilder.Append('5');
        assertEquals("5", exprBuilder.GetExpression());
    }

    @Test
    public void Test22() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('-');
        exprBuilder.Append('-');
        exprBuilder.Append('5');
        assertEquals("-5", exprBuilder.GetExpression());
    }

    @Test
    public void Test3() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('+');
        exprBuilder.Append('-');
        exprBuilder.Append('5');
        assertEquals("-5", exprBuilder.GetExpression());
    }

    @Test
    public void Test4() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('-');
        exprBuilder.Append('-');
        exprBuilder.Append('5');
        assertEquals("-5", exprBuilder.GetExpression());
    }

    @Test
    public void Test5() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('0');
        exprBuilder.Append('0');
        assertEquals("0", exprBuilder.GetExpression());
    }

    @Test
    public void Test6() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('1');
        exprBuilder.Append('1');
        assertEquals("11", exprBuilder.GetExpression());
    }

    @Test
    public void Test7() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('-');
        exprBuilder.Append('1');
        assertEquals("-1", exprBuilder.GetExpression());

        exprBuilder.Append('1');
        exprBuilder.Append('+');
        exprBuilder.Append('2');
        assertEquals("-11 + 2", exprBuilder.GetExpression());
        exprBuilder.ReduceExpression();
        assertEquals("-9", exprBuilder.GetExpression());
    }

    @Test
    public void Test9() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('+');
        exprBuilder.Append('1');
        assertEquals("1", exprBuilder.GetExpression());

        exprBuilder.Append('1');
        exprBuilder.Append('-');
        exprBuilder.Append('1');
        exprBuilder.Append('2');
        assertEquals("11 - 12", exprBuilder.GetExpression());

        exprBuilder.ReduceExpression();
        assertEquals("-1", exprBuilder.GetExpression());
    }

    @Test
    public void Test10() {
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('+');
        exprBuilder.Append('1');
        assertEquals("1", exprBuilder.GetExpression());

        exprBuilder.Reset();

        assertEquals("", exprBuilder.GetExpression());
        assertEquals("", exprBuilder.GetCalculationPreview());
    }

    @Test
    public void Test_Preview1(){
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('1');
        assertEquals("1", exprBuilder.GetCalculationPreview());

        exprBuilder.Append('+');
        assertEquals("1", exprBuilder.GetCalculationPreview());

        exprBuilder.Append('2');
        assertEquals("3", exprBuilder.GetCalculationPreview());

        exprBuilder.Append('-');
        assertEquals("3", exprBuilder.GetCalculationPreview());

        exprBuilder.Append('1');
        assertEquals("2", exprBuilder.GetCalculationPreview());
    }

    @Test
    public void Test_Reduce1(){
        ExpressionBuilder exprBuilder = GetExpressionBuilder();
        exprBuilder.Append('1');
        exprBuilder.ReduceExpression();
        assertEquals("1", exprBuilder.GetExpression());

        exprBuilder.Append('+');
        exprBuilder.ReduceExpression();
        assertEquals("1 +", exprBuilder.GetExpression());

        exprBuilder.Append('2');
        exprBuilder.ReduceExpression();
        assertEquals("3", exprBuilder.GetExpression());
    }

    private ExpressionBuilder GetExpressionBuilder() {
        CalculatorImpl calculator = new CalculatorImpl();
        ExpressionBuilder exprBuilder = new ExpressionBuilder(calculator);
        return exprBuilder;
    }
}
