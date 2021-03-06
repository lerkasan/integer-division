package ua.com.foxminded.integerdivision.math;

import ua.com.foxminded.integerdivision.math.operation.addition.IntegerAddition;
import ua.com.foxminded.integerdivision.math.operation.division.IntegerDivision;
import ua.com.foxminded.integerdivision.math.operation.multiplication.IntegerMultiplication;
import ua.com.foxminded.integerdivision.math.operation.subtraction.IntegerSubtraction;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import java.math.BigInteger;
import java.util.*;

public class MathExpressionParser {

    private static final String NULL_INFIX = "Null infix string can't be converted to postfix.";
    private static final String NULL_POSTFIX = "Null postfix string can't be evaluated.";
    private static final String INVALID_SYMBOL = "Can't evaluate expression due to invalid symbol ";
    private static final String LETTERS_NOT_ALLOWED = "Letters are not allowed in arithmetic expressions. Invalid symbol ";
    private static final String DECIMALS_NOT_ALLOWED = "Only integer numbers are allowed in the expression. Invalid symbol ";
    private static final String WRONG_PARENTHESES_ORDER = "Wrong order of parentheses near position ";
    private static final String WRONG_OPERATOR_ORDER = "Wrong order of operators near symbol ";
    private static final String AT_POSITION = " at position ";
    private static final String STEP = "Step %d:\n\n";

    protected String replaceUnaryMinuses(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException(NULL_INFIX);
        }
        String infixWithoutSpaces = infix.replaceAll(" ", "");
        StringBuilder result = new StringBuilder();
        Set<Character> operators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('+', '-', '*', '/', '(')));
        boolean isPreviousCharOperator = false;
        boolean isParenthesesOpened = false;
        char[] infixChars = infixWithoutSpaces.toCharArray();
        for (int index = 0; index < infixChars.length; index++) {
            boolean isLastCharacter = (index == infixChars.length - 1);
            boolean isNextCharacterDigit = !isLastCharacter && Character.isDigit(infixChars[index + 1]);
            if ((infixChars[index] == '-') && (!isLastCharacter && isNextCharacterDigit) && ((index == 0) || isPreviousCharOperator)) {
                result.append("(0");
                isParenthesesOpened = true;
            }
            boolean isNextCharacterOperator = !isLastCharacter && !Character.isDigit(infixChars[index+1]);
            result.append(infixChars[index]);
            if (isParenthesesOpened && (isLastCharacter || isNextCharacterOperator)) {
                result.append(")");
                isParenthesesOpened = false;
            }
            isPreviousCharOperator = operators.contains(infixChars[index]);
        }
        return result.toString();
    }

    public String convertInfixToPostfix(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException(NULL_INFIX);
        }
        String alteredInfix = replaceUnaryMinuses(infix);
        char[] infixChars = alteredInfix.toCharArray();
        Deque<Character> operatorStack = new ArrayDeque<>();
        String postfix = "";
        int parenthesesCounter = 0;
        int position = 0;
        boolean isPreviousOperator = true;
        boolean isPreviousOpeningParentheses = false;
        boolean isPreviousClosingParentheses = false;
        for (char infixChar : infixChars) {
            position++;
            if (Character.isLetter(infixChar)) {
                throw new IllegalArgumentException(LETTERS_NOT_ALLOWED + infixChar + AT_POSITION + position + "\n"
                        + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
            }
            if (Character.isDigit(infixChar)) {
                if (isPreviousClosingParentheses) {
                    throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + AT_POSITION + position + "\n"
                            + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                }
                postfix += infixChar;
                isPreviousOperator = false;
                isPreviousOpeningParentheses = false;
                isPreviousClosingParentheses = false;
            } else {
                char operatorFromStack = ' ';
                switch (infixChar) {
                    case '(':
                        if ((!isPreviousOperator) && (!isPreviousOpeningParentheses)){
                            throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + AT_POSITION + position + "\n"
                                    + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                        }
                        operatorStack.push(infixChar);
                        isPreviousOperator = false;
                        isPreviousOpeningParentheses = true;
                        isPreviousClosingParentheses = false;
                        parenthesesCounter++;
                        break;
                    case ')':
                        if (parenthesesCounter <= 0) {
                            throw new IllegalArgumentException(WRONG_PARENTHESES_ORDER + position + "\n"
                                    + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                        }
                        isPreviousOperator = false;
                        isPreviousOpeningParentheses = false;
                        isPreviousClosingParentheses = true;
                        parenthesesCounter--;
                        while (!operatorStack.isEmpty() && (operatorFromStack != '(')) {
                            operatorFromStack = operatorStack.pop();
                            if (operatorFromStack != '(') {
                                postfix = postfix.trim() + " " + operatorFromStack + " ";
                            }
                        }
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        if ((isPreviousOperator) && (infixChar != '-')) {
                            throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + AT_POSITION + position + "\n"
                                    + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                        }
                        isPreviousOperator = true;
                        isPreviousOpeningParentheses = false;
                        isPreviousClosingParentheses = false;
                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
                        break;
                    case ' ':
                        break;
                    case '.':
                    case ',':
                        throw new IllegalArgumentException(DECIMALS_NOT_ALLOWED + infixChar + AT_POSITION + position + "\n"
                                + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                    default:
                        throw new IllegalArgumentException(INVALID_SYMBOL + infixChar + AT_POSITION + position + "\n"
                                + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                }
            }
        }
        if (parenthesesCounter != 0) {
            throw new IllegalArgumentException(WRONG_PARENTHESES_ORDER + position + "\n"
                    + alteredInfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
        }
        while (!operatorStack.isEmpty()) {
            postfix = postfix.trim() + " " + operatorStack.pop() + " ";
        }
        return postfix.trim();
    }

    private String prioritizeStackOperators(Deque<Character> operatorStack, char infixChar) {
        int stackOperatorPriority = 0;
        char operatorFromStack;
        StringBuilder outputToPostfix = new StringBuilder();
        if (operatorStack.isEmpty()) {
            operatorStack.push(infixChar);
        } else {
            int infixOperatorPriority = (infixChar == '+') || (infixChar == '-') ? 1 : 2;
            do {
                operatorFromStack = operatorStack.pop();
                if (operatorFromStack == '(') {
                    operatorStack.push(operatorFromStack);
                } else {
                    if ((operatorFromStack == '+') || (operatorFromStack == '-')) {
                        stackOperatorPriority = 1;
                    }
                    if ((operatorFromStack == '*') || (operatorFromStack == '/')) {
                        stackOperatorPriority = 2;
                    }
                    if (stackOperatorPriority < infixOperatorPriority) {
                        operatorStack.push(operatorFromStack);
                    } else {
                        outputToPostfix.append(operatorFromStack + " ");
                    }
                }
            } while (!operatorStack.isEmpty() && (operatorFromStack != '(') && (stackOperatorPriority >= infixOperatorPriority));
            operatorStack.push(infixChar);
        }
        return outputToPostfix.toString();
    }

    // when we pass a formatterFactory parameter it is obvious that we want verbose output
    public BigInteger evaluatePostfixExpression(String postfix, FormatterFactory formatterFactory) {
        boolean verbose = true;
        return evaluatePostfixExpression(postfix, formatterFactory, verbose);
    }

    // to maintain compliance with existing unit tests we will use classicFormatterFactory as default formatter factory
    public BigInteger evaluatePostfixExpression(String postfix, boolean verbose) {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        return evaluatePostfixExpression(postfix, formatterFactory, verbose);
    }

    public BigInteger evaluatePostfixExpression(String postfix, FormatterFactory formatterFactory, boolean verbose) {
        if (postfix == null) {
            throw new IllegalArgumentException(NULL_POSTFIX);
        }
        char[] postfixChars = postfix.toCharArray();
        Deque<BigInteger> operandStack = new ArrayDeque<>();
        Formatter formatter;
        String operand = "";
        BigInteger firstOperand;
        BigInteger secondOperand;
        int step = 0;
        int position = 0;
        for (char postfixChar : postfixChars) {
            position++;
            if (Character.isDigit(postfixChar)) {
                operand += postfixChar;
            } else {
                if (!"".equals(operand)) {
                    operandStack.push(new BigInteger(operand));
                    operand = "";
                }
                if (postfixChar != ' ') {
                    if (operandStack.size() >= 2) {
                        secondOperand = operandStack.pop();
                        firstOperand = operandStack.pop();
                    } else {
                        throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + postfixChar + AT_POSITION + position + "\n"
                                + postfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                    }
                    BigInteger result;
                    switch (postfixChar) {
                        case '+':
                            IntegerAddition addition = new IntegerAddition(firstOperand, secondOperand);
                            result = addition.getNumericResult();
                            if (verbose) {
                                step++;
                                formatter = formatterFactory.getAdditionFormatter();
                                System.out.printf(STEP, step);
                                System.out.println(formatter.formatOutput(addition) + "\n");
                            }
                            break;
                        case '-':
                            IntegerSubtraction subtraction = new IntegerSubtraction(firstOperand, secondOperand);
                            result = subtraction.getNumericResult();
                            if (verbose) {
                                step++;
                                System.out.printf(STEP, step);
                                formatter = formatterFactory.getSubtractionFormatter();
                                System.out.println(formatter.formatOutput(subtraction) + "\n");
                            }
                            break;
                        case '*':
                            IntegerMultiplication multiplication = new IntegerMultiplication(firstOperand, secondOperand);
                            result = multiplication.getNumericResult();
                            if (verbose) {
                                step++;
                                formatter = formatterFactory.getMultiplicationFormatter();
                                System.out.printf(STEP, step);
                                System.out.println(formatter.formatOutput(multiplication) + "\n");
                            }
                            break;
                        case '/':
                            IntegerDivision division = new IntegerDivision(firstOperand, secondOperand);
                            result = division.getNumericResult();
                            if (verbose) {
                                step++;
                                formatter = formatterFactory.getDivisionFormatter();
                                System.out.printf(STEP, step);
                                System.out.println(formatter.formatOutput(division) + "\n");
                            }
                            break;
                        default:
                            throw new IllegalArgumentException(INVALID_SYMBOL + postfixChar + AT_POSITION + position + "\n"
                                    + postfix + "\n" + Formatter.getOffsetSpaces(position-1) + "^");
                    }
                    operandStack.push(result);
                }
            }
        }
        return operandStack.pop();
    }

    public BigInteger evaluate(String infix, FormatterFactory formatterFactory, boolean verbose) {
        String postfix = convertInfixToPostfix(infix);
        return evaluatePostfixExpression(postfix, formatterFactory, verbose);
    }

    // when we pass a formatterFactory parameter it is obvious that we want verbose output
    public BigInteger evaluate(String infix, FormatterFactory formatterFactory) {
        boolean verbose = true;
        String postfix = convertInfixToPostfix(infix);
        return evaluatePostfixExpression(postfix, formatterFactory, verbose);
    }

    // to maintain compliance with existing unit tests we will use classicFormatterFactory as default formatter factory
    public BigInteger evaluate(String infix, boolean verbose) {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        String postfix = convertInfixToPostfix(infix);
        return evaluatePostfixExpression(postfix, formatterFactory, verbose);
    }
}
