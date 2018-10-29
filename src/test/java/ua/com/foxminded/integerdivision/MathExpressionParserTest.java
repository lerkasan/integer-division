package ua.com.foxminded.integerdivision;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathExpressionParserTest extends MathExpressionParser {

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
}
