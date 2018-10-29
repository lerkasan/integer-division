package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.*;

public class MathExpressionParser {

    private static final String NULL_INFIX = "Null infix string can't be converted to postfix.";
    private static final String NULL_POSTFIX = "Null postix string can't be evaluated.";
    private static final String INVALID_SYMBOL = "Can't evaluate expression due to invalid symbol ";
    private static final String LETTERS_NOT_ALLOWED = "Letters are not allowed in arithmetic expressions. Invalid symbol ";
    private static final String DECIMALS_NOT_ALLOWED = "Only integer numbers are allowed in the expression. Invalid symbol ";
    private static final String WRONG_PARENTHESES_ORDER = "Wrong order of parentheses near position ";
    private static final String WRONG_OPERATOR_ORDER = "Wrong order of operators near symbol ";

    protected String replaceUnaryMinuses(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException(NULL_INFIX);
        }
        String infixWithoutSpaces = infix.replaceAll(" ", "");
        String result = "";
        Set<Character> operators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('+', '-', '*', '/', '(')));
        boolean isPreviousCharOperator = false;
        boolean isParenthesesOpened = false;
        char[] infixChars = infixWithoutSpaces.toCharArray();
        for (int index = 0; index < infixChars.length; index++) {
            boolean isLastCharacter = (index == infixChars.length - 1);
            boolean isNextCharacterDigit = !isLastCharacter && Character.isDigit(infixChars[index + 1]);
            if ((infixChars[index] == '-') && ((index == 0) || isPreviousCharOperator)) {
                if (!isLastCharacter && isNextCharacterDigit) {
                    result += "(0";
                    isParenthesesOpened = true;
                }
            }
            boolean isNextCharacterOperator = !isLastCharacter && !Character.isDigit(infixChars[index+1]);
            result += infixChars[index];
            if (isParenthesesOpened && (isLastCharacter || isNextCharacterOperator)) {
                result += ")";
                isParenthesesOpened = false;
            }
            if (operators.contains(infixChars[index])) {
                isPreviousCharOperator = true;
            } else {
                isPreviousCharOperator = false;
            }
        }
        return result;
    }

    public String convertInfixToPostfix(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException(NULL_INFIX);
        }
        String alteredInfix = replaceUnaryMinuses(infix);
//        System.out.println(alteredInfix);
        char[] infixChars = alteredInfix.toCharArray();
        Deque<Character> operatorStack = new ArrayDeque<>();
        String postfix = "";
        int parenthesesCounter = 0;
        int position = 0;
        boolean isPreviousCharOperator = false;
        Formatter formatter = new Formatter();
        for (char infixChar : infixChars) {
            position++;
            if (Character.isLetter(infixChar)) {
                throw new IllegalArgumentException(LETTERS_NOT_ALLOWED + infixChar + " at position " + position + "\n"
                        + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
            }
            if (Character.isDigit(infixChar)) {
                postfix += infixChar;
                isPreviousCharOperator = false;
            } else {
                char operatorFromStack = ' ';
                switch (infixChar) {
                    case '(':
                        operatorStack.push(infixChar);
                        isPreviousCharOperator = false;
                        parenthesesCounter++;
                        break;

                    case ')':
                        if (parenthesesCounter <= 0) {
                            throw new IllegalArgumentException(WRONG_PARENTHESES_ORDER + position + "\n"
                                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                        }
                        isPreviousCharOperator = false;
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
                        if ((isPreviousCharOperator) && (infixChar != '-')) {
                            throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + " at position " + position + "\n"
                                    + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                        }
                        isPreviousCharOperator = true;
                        if ((postfix == "") || ((!operatorStack.isEmpty()) && (operatorStack.peek() == '('))) {
//                            if (infixChar != '-') {
                            if ((infixChar == '*') || (infixChar == '/')) {
                                throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + infixChar + " at position " + position + "\n"
                                        + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                            }
//                            postfix =  postfix.trim() + " 0 ";
                        }
                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
                        break;

                    case ' ':
                        break;

                    case '.':
                    case ',':
                        throw new IllegalArgumentException(DECIMALS_NOT_ALLOWED + infixChar + " at position " + position + "\n"
                                + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");

                    default:
                        throw new IllegalArgumentException(INVALID_SYMBOL + infixChar+ " at position " + position + "\n"
                                + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                }
            }
        }
        if (parenthesesCounter != 0) {
            throw new IllegalArgumentException(WRONG_PARENTHESES_ORDER);
        }
        while (!operatorStack.isEmpty()) {
            postfix = postfix.trim() + " " + operatorStack.pop() + " ";
        }
        return postfix.trim();
    }

    private String prioritizeStackOperators(Deque<Character> operatorStack, char infixChar) {
        int stackOperatorPriority = 0;
        char operatorFromStack;
        String outputToPostfix = "";
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
                        outputToPostfix += operatorFromStack + " ";
                    }
                }
            } while (!operatorStack.isEmpty() && (operatorFromStack != '(') && (stackOperatorPriority >= infixOperatorPriority));
            operatorStack.push(infixChar);
        }
        return outputToPostfix;
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
                if (operand != "") {
                    operandStack.push(operand);
                    operand = "";
                }
                if (postfixChar != ' ') {
//                    if ((operandStack.size() < 2) && ((postfixChar == '-') || (postfixChar == '+'))) {
//                        operand = "" + postfixChar;
//                        continue;
//                    }

                    if (operandStack.size() >= 2) {
                        secondOperand = operandStack.pop();
                        firstOperand = operandStack.pop();
                    } else {
                        throw new IllegalArgumentException(WRONG_OPERATOR_ORDER + postfixChar + " at position " + position + "\n"
                                + postfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                    }
                    String result = "";
                    switch (postfixChar) {
                        case '+':
                            IntegerAddition addition = new IntegerAddition(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = addition.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(addition + "\n");
                            }
                            break;
                        case '-':
                            IntegerSubtraction substraction = new IntegerSubtraction(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = substraction.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(substraction + "\n");
                            }
                            break;
                        case '*':
                            IntegerMultiplication multiplication = new IntegerMultiplication(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = multiplication.getProduct();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(multiplication + "\n");
                            }
                            break;

                        case '/':
                            IntegerDivision division = new IntegerDivision(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = division.getQuotient();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(division + "\n");
                            }
                            break;

                        default:
                            throw new IllegalArgumentException(INVALID_SYMBOL + postfixChar + " at position " + position + "\n"
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
