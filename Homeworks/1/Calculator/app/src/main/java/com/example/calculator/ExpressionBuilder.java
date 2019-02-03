package com.example.calculator;

public class ExpressionBuilder {
    private CalculatorImpl calculator;
    private String partsDivider = " ";
    private StringBuilder expressionStringBuilder = new StringBuilder();
    private int maxLength = 22;

    public ExpressionBuilder(CalculatorImpl calculator) {
        this.calculator = calculator;
    }

    public String GetExpression() {
        return expressionStringBuilder.toString();
    }

    public void Append(char nextLetter) {
        if (IsAcceptable(nextLetter)) {
            AppendInner(nextLetter);
            return;
        }

        boolean isOperator = IsOperator(nextLetter);
        boolean lastWasOperator = IsOperator(GetLastChar());

        if (isOperator && lastWasOperator) {
            if (expressionStringBuilder.length() == 1) {
                expressionStringBuilder.setLength(expressionStringBuilder.length() - 1);
            } else {
                expressionStringBuilder.setLength(expressionStringBuilder.length() - 1 - partsDivider.length());
            }
            Append(nextLetter);
            return;
        }

        if (isOperator) {
            ReduceExpression();
            if (IsAcceptable(nextLetter)) {
                AppendInner(nextLetter);
                return;
            }

            throw new IllegalArgumentException();
        }

        throw new IllegalArgumentException();
    }

    private void AppendInner(char nextLetter) {
        if(expressionStringBuilder.length() + 1 > maxLength){
            return;
        }

        if(expressionStringBuilder.length() == 0 && nextLetter == '+'){
            return;
        }

        if(expressionStringBuilder.length() == 1 && expressionStringBuilder.charAt(0) == '-' && nextLetter == '+'){
            expressionStringBuilder.setLength(0);
            return;
        }

        if (expressionStringBuilder.length() == 0
                || expressionStringBuilder.length() == 1 && expressionStringBuilder.charAt(0) == '-') {
            expressionStringBuilder.append(nextLetter);
            return;
        }

        if (IsOperator(nextLetter) || IsOperator(GetLastChar())) {
            expressionStringBuilder.append(partsDivider);
        }

        expressionStringBuilder.append(nextLetter);
    }

    private boolean IsAcceptable(char nextLetter) {
        if (expressionStringBuilder.length() == 0) {
            return true;
        }

        if (IsOperator(nextLetter)) {
            return !IsOperator(GetLastChar()) && !HasAnyOperator();
        }

        char lastChar = GetLastChar();

        if (Character.isDigit(lastChar) && Character.isDigit(nextLetter)) {
            //23
            return true;
        }

        if (IsOperator(lastChar) && Character.isDigit(nextLetter)) {
            //+2
            return true;
        }

        if (Character.isDigit(lastChar) && IsOperator(nextLetter)) {
            //2+
            return true;
        }

        if (IsOperator(lastChar) && IsOperator(nextLetter)) {
            //++
            return false;
        }

        throw new IllegalArgumentException();
    }

    public String GetCalculationPreview() {
        if (HasAnyOperator()) {
            if (IsOperator(GetLastChar())) {
                // 1 +
                return expressionStringBuilder.substring(0, expressionStringBuilder.length() - 2);
            }

            // 1 + 2
            return calculator.Calculate(GetExpression(), partsDivider);
        }

        // 1
        return GetExpression();
    }

    private boolean HasAnyOperator() {
        for (int i = 0; i < expressionStringBuilder.length(); i++) {
            if (expressionStringBuilder.charAt(i) == '+' || expressionStringBuilder.charAt(i) == '-') {
                if (i == 0) {
                    continue;
                }

                return true;
            }
        }

        return false;
    }

    public void ReduceExpression() {
        if (!HasAnyOperator()) {
            // 1
            return;
        }

        if (IsOperator(GetLastChar())) {
            // 1 +
            return;
        }

        // 1 + 2
        String expression = GetExpression();
        expressionStringBuilder.setLength(0);
        expressionStringBuilder.append(calculator.Calculate(expression, partsDivider));
    }

    public void Reset() {
        expressionStringBuilder.setLength(0);
    }

    private boolean IsOperator(char letter) {
        return letter == '+' || letter == '-';
    }

    private char GetLastChar() {
        return expressionStringBuilder.charAt(expressionStringBuilder.length() - 1);
    }
}
