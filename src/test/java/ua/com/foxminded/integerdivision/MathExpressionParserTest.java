package ua.com.foxminded.integerdivision;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathExpressionParserTest extends MathExpressionParser {

    private static final String NULL_INFIX = "Null infix string can't be converted to postfix.";
    private static final String NULL_POSTFIX = "Null postix string can't be evaluated.";
    private static final String INVALID_SYMBOL = "Can't evaluate expression due to invalid symbol ";
    private static final String LETTERS_NOT_ALLOWED = "Letters are not allowed in arithmetic expressions. Invalid symbol ";
    private static final String DECIMALS_NOT_ALLOWED = "Only integer numbers are allowed in the expression. Invalid symbol ";
    private static final String WRONG_PARENTHESES_ORDER = "Wrong order of parentheses near position ";
    private static final String WRONG_OPERATOR_ORDER = "Wrong order of operators near symbol ";

    private static MathExpressionParser underTest;

    @Test
    void shouldReplaceUnaryMinuses() {
        String expression = "-67 + -42/2 -(15-4)*-7-3*-9 + 8/-2";
        underTest = new MathExpressionParser();
        String actual = underTest.replaceUnaryMinuses(expression);
        String expected = "(0-67)+(0-42)/2-(15-4)*(0-7)-3*(0-9)+8/(0-2)";
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWithNullInfixDuringReplacementOfUnaryMinuses() {
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.replaceUnaryMinuses(null));
        assertEquals(NULL_INFIX, exception.getMessage());
    }

    @Test
    void shouldConvertToPostfixExpressionWithNestedParentheses() {
        String expression = "-5+3*((0-4)+16)";
        underTest = new MathExpressionParser();
        String actual = underTest.convertInfixToPostfix(expression);
        String expected = "0 5 - 3 0 4 - 16 + * +";
        assertEquals(expected, actual);
    }

    @Test
    void shouldConvertToPostfixExpressionWithNegativeNumbers() {
        String expression = "98 + 7 * -2-36/4 + 3*(-4+16)";
        underTest = new MathExpressionParser();
        String actual = underTest.convertInfixToPostfix(expression);
        String expected = "98 7 0 2 - * + 36 4 / - 3 0 4 - 16 + * +";
        assertEquals(expected, actual);
    }

    @Test
    void shouldConvertToPostfixExpressionWithParenthesesAndNegativeNumbers() {
        String expression = "-25-(98-4)/-2 + 7 * -2-36/4 + 3*(-4+16)";
        underTest = new MathExpressionParser();
        String actual = underTest.convertInfixToPostfix(expression);
        String expected = "0 25 - 98 4 - 0 2 - / - 7 0 2 - * + 36 4 / - 3 0 4 - 16 + * +";
        assertEquals(expected, actual);
    }

    @Test
    void shouldEvaluatePostfixWithNegativeNumbers() {
        String expression = "0 67 - 0 42 - 2 / + 15 4 - 0 7 - * - 3 0 9 - * - 8 0 2 - / +"; //infix = "-67+-42/2-(15-4)*-7-3*-9+8/-2";
        underTest = new MathExpressionParser();
        boolean verbose = true;
        String actual = underTest.evaluatePostfixExpression(expression, verbose);
        String expected = "12";
        assertEquals(expected, actual);
    }

    @Test
    void shouldEvaluatePostfixWithNestedParentheses() {
        String expression = "0 5 - 3 0 4 - 16 + * +"; //infix = "-5+3*((0-4)+16)";
        underTest = new MathExpressionParser();
        boolean verbose = true;
        String actual = underTest.evaluatePostfixExpression(expression, verbose);
        String expected = "31";
        assertEquals(expected, actual);
    }

    @Test
    void shouldEvaluateInfixStartingWithParentheses() {
        String expression = "(15+6)";
        underTest = new MathExpressionParser();
        boolean verbose = true;
        String actual = underTest.evaluate(expression, verbose);
        String expected = "21";
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWrongOperatorOrderWhenInfixStartingWithUnaryPlus() {
        String expression = "+15+6";
        String errorMessage = WRONG_OPERATOR_ORDER + "+ at position 1\n" + expression + "\n^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderExceptionWhenInfixStartsWithMultiplication() {
        String expression = "*(15+6)";
        String errorMessage = WRONG_OPERATOR_ORDER + "* at position 1\n" + expression + "\n^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderExceptionWhenInfixStartsWithDivision() {
        String expression = "/15+6";
        String errorMessage = WRONG_OPERATOR_ORDER + "/ at position 1\n" + expression + "\n^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderExceptionWithoutOperatorBetweenNumberAndOpeningParentheses() {
        String expression = "8(15+6)";
        String errorMessage = WRONG_OPERATOR_ORDER + "( at position 2\n" + expression + "\n ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldEvaluateInfixWithNumberMultipliedByParentheses() {
        String expression = "8*(15+6)";
        underTest = new MathExpressionParser();
        boolean verbose = true;
        String actual = underTest.evaluate(expression, verbose);
        String expected = "168";
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWrongOperatorOrderExceptionWithoutOperatorBetweenNumberAndClosingParentheses() {
        String expression = "(15+6)9";
        String errorMessage = WRONG_OPERATOR_ORDER + "9 at position 7\n" + expression + "\n      ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldEvaluateInfixWithParenthesesMultipliedByNumber() {
        String expression = "(15+6)*9";
        underTest = new MathExpressionParser();
        boolean verbose = true;
        String actual = underTest.evaluate(expression, verbose);
        String expected = "189";
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWrongParenthesesOrderWithoutOpeningParentheses() {
        String expression = "15+6)*9";
        String errorMessage = WRONG_PARENTHESES_ORDER + "5\n" + expression + "\n    ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongParenthesesOrderWithoutClosingParentheses() {
        String expression = "(5+6*9";
        String errorMessage = WRONG_PARENTHESES_ORDER + "6\n" + expression + "\n     ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderWithoutSecondOperand() {
        String expression = "(5+6)+";
        String errorMessage = WRONG_OPERATOR_ORDER + "+ at position 7\n" + "5 6 + +\n" + "      ^";
        boolean verbose = true;
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.evaluate(expression, verbose));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderWithoutClosingParentheses() {
        String expression = "(5+6";
        String errorMessage = WRONG_PARENTHESES_ORDER + "4\n" + expression + "\n   ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderWithConsecutiveOperators() {
        String expression = "5+*6";
        String errorMessage = WRONG_OPERATOR_ORDER + "* at position 3\n" + expression + "\n  ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderWhenInfixStartsWithMultiplication() {
        String expression = "(*67-42/2)";
        String errorMessage = WRONG_OPERATOR_ORDER + "* at position 4\n" + "67 * 42 2 / -" + "\n   ^";
        boolean verbose = true;
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.evaluate(expression, verbose));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowWrongOperatorOrderWhenInfixStartsWithDivision() {
        String expression = "(/67-42*2)";
        String errorMessage = WRONG_OPERATOR_ORDER + "/ at position 4\n" + "67 / 42 2 * -" + "\n   ^";
        boolean verbose = true;
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.evaluate(expression, verbose));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldEvaluateWhenInfixStartsWithUnaryMinus() {
        String expression = "(-67-42/2)";
        boolean verbose = true;
        underTest = new MathExpressionParser();
        String actual = underTest.evaluate(expression, verbose);
        assertEquals("-88", actual);
    }

    @Test
    void shouldThrowWrongOperatorOrderWhenInfixStartsWithUnaryPlus() {
        String expression = "(+67-42*2)";
        boolean verbose = true;
        String errorMessage = WRONG_OPERATOR_ORDER + "( at position 1\n" + expression + "\n^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.evaluatePostfixExpression(expression, verbose));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullInfix() {
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(null));
        assertEquals(NULL_INFIX, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullPostfix() {
        underTest = new MathExpressionParser();
        boolean verbose = true;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.evaluatePostfixExpression(null, verbose));
        assertEquals(NULL_POSTFIX, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithInvalidSymbol() {
        String expression = "(5+6@)";
        String errorMessage = INVALID_SYMBOL + "@ at position 5\n" + expression + "\n    ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithQuestionMark() {
        String expression = "24?5+52";
        String errorMessage = INVALID_SYMBOL + "? at position 3\n" + expression + "\n  ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithLetterSymbol() {
        String expression = "(4-A*(6-3))";
        String errorMessage = LETTERS_NOT_ALLOWED + "A at position 4\n" + expression + "\n   ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithPointDecimalNumber() {
        String expression = "(4-52.7*(6-3))";
        String errorMessage = DECIMALS_NOT_ALLOWED + ". at position 6\n" + expression + "\n     ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithCommaDecimalNumber() {
        String expression = "(4-52,7*(6-3))";
        String errorMessage = DECIMALS_NOT_ALLOWED + ", at position 6\n" + expression + "\n     ^";
        underTest = new MathExpressionParser();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.convertInfixToPostfix(expression));
        assertEquals(errorMessage, exception.getMessage());
    }
}
