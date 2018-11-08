package ua.com.foxminded.integerdivision.math;

import ua.com.foxminded.integerdivision.text.Formatter;

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
        Formatter formatter = new Formatter();
        for (char infixChar : infixChars) {
            position++;
            if (Character.isLetter(infixChar)) {
                throw new IllegalArgumentException(LETTERS_NOT_ALLOWED + infixChar + AT_POSITION + position + "\n"
                        + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
            }
            if (Character.isDigit(infixChar)) {
                if (isPreviousClosingParentheses) {
                    throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + AT_POSITION + position + "\n"
                            + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                                + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                    default:
                        throw new IllegalArgumentException(INVALID_SYMBOL + infixChar + AT_POSITION + position + "\n"
                                + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                }
            }
        }
        if (parenthesesCounter != 0) {
            throw new IllegalArgumentException(WRONG_PARENTHESES_ORDER + position + "\n"
                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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

    public String evaluatePostfixExpression(String postfix, boolean verbose) {
        if (postfix == null) {
            throw new IllegalArgumentException(NULL_POSTFIX);
        }
        char[] postfixChars = postfix.toCharArray();
        Deque<String> operandStack = new ArrayDeque<>();
        Formatter formatter = new Formatter();
        String operand = "";
        String firstOperand = "";
        String secondOperand = "";
        int step = 0;
        int position = 0;
        for (char postfixChar : postfixChars) {
            position++;
            if (Character.isDigit(postfixChar)) {
                operand += postfixChar;
            } else {
                if (!"".equals(operand)) {
                    operandStack.push(operand);
                    operand = "";
                }
                if (postfixChar != ' ') {
                    if (operandStack.size() >= 2) {
                        secondOperand = operandStack.pop();
                        firstOperand = operandStack.pop();
                    } else {
                        throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + postfixChar + AT_POSITION + position + "\n"
                                + postfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                    }
                    String result = "";
                    switch (postfixChar) {
                        case '+':
                            IntegerAddition addition = new IntegerAddition(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = addition.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf(STEP, step);
                                System.out.println(addition + "\n");
                            }
                            break;
                        case '-':
                            IntegerSubtraction subtraction = new IntegerSubtraction(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = subtraction.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf(STEP, step);
                                System.out.println(subtraction + "\n");
                            }
                            break;
                        case '*':
                            IntegerMultiplication multiplication = new IntegerMultiplication(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = multiplication.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf(STEP, step);
                                System.out.println(multiplication + "\n");
                            }
                            break;
                        case '/':
                            IntegerDivision division = new IntegerDivision(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = division.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf(STEP, step);
                                System.out.println(division + "\n");
                            }
                            break;
                        default:
                            throw new IllegalArgumentException(INVALID_SYMBOL + postfixChar + AT_POSITION + position + "\n"
                                    + postfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                    }
                    operandStack.push(result);
                }
            }
        }
        return operandStack.pop();
    }

    public String evaluate(String infix, boolean verbose) {
        String postfix = convertInfixToPostfix(infix);
        return evaluatePostfixExpression(postfix, verbose);
    }
}
