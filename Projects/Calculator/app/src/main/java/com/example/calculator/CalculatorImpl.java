package com.example.calculator;

public final class CalculatorImpl {
    private String _operation;
    private String _leftOperand;
    private String _rightOperand;

    public String GetOperation(){
        return _operation;
    }

    public String GetExpression(){
        return _leftOperand + " " + _operation + " " + _rightOperand;
    }

    public String Calculate(String expression, String partsDivider){
        String[] exrParts = expression.split(partsDivider);
        return Calculate(exrParts[0], exrParts[1], exrParts[2]);
    }

    public String Calculate(String left, String operator, String right){
        long result = 0;
        if (operator.equals("+")){
            result = Long.parseLong(left) + Long.parseLong(right);
        }
        if (operator.equals("-")){
            result = Long.parseLong(left) - Long.parseLong(right);
        }

        return String.valueOf(result);
    }
}
