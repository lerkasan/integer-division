package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.*;

public class MathExpressionParser {

    private String replaceUnaryMinuses(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException("Null infix string can't be converted to postfix.");
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
            throw new IllegalArgumentException("Null infix string can't be converted to postfix.");
        }
        String alteredInfix = replaceUnaryMinuses(infix);
        System.out.println(alteredInfix);
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
                throw new IllegalArgumentException("Letters are not allowed in arithmetic expressions. Invalid symbol " + infixChar + " at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                            throw new IllegalArgumentException("Wrong order of parentheses at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
                            throw new IllegalArgumentException("Wrong order of operators near symbol " + infixChar + " at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                        }
                        isPreviousCharOperator = true;
                        if ((postfix == "") || ((!operatorStack.isEmpty()) && (operatorStack.peek() == '('))) {
//                            if (infixChar != '-') {
                            if ((infixChar == '*') || (infixChar == '/')) {
                                throw new IllegalArgumentException("Wrong order of operators near symbol " + infixChar + " at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                            }
//                            postfix =  postfix.trim() + " 0 ";
                        }
                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
                        break;

                    case ' ':
                        break;

                    case '.':
                    case ',':
                        throw new IllegalArgumentException("Only integer numbers are allowed in the expression. Invalid symbol " + infixChar + " at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");

                    default:
                        throw new IllegalArgumentException("Expression contains some invalid characters. Invalid symbol " + infixChar+ " at position " + position + "\n" + alteredInfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                }
            }
        }
        if (parenthesesCounter != 0) {
            throw new IllegalArgumentException("Invalid order of parentheses");
        }
        while (!operatorStack.isEmpty()) {
            postfix = postfix.trim() + " " + operatorStack.pop() + " ";
        }
        return  postfix;
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
            throw new IllegalArgumentException("Null postix string can't be evaluated.");
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
                        throw new IllegalArgumentException("Wrong order of operators near symbol " + postfixChar + " at position " + position + "\n" + postfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
                    }
                    String result = "";
                    switch (postfixChar) {
                        case '+':
                            IntegerAddition addition = new IntegerAddition(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = addition.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(addition);
                                System.out.println();
                            }
                            break;
                        case '-':
                            IntegerSubtraction substraction = new IntegerSubtraction(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = substraction.getResult();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(substraction);
                                System.out.println();
                            }
                            break;
                        case '*':
                            IntegerMultiplication multiplication = new IntegerMultiplication(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = multiplication.getProduct();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(multiplication);
                                System.out.println();
                            }
                            break;

                        case '/':
                            IntegerDivision division = new IntegerDivision(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = division.getQuotient();
                            if (verbose) {
                                step++;
                                System.out.printf("Step %d:\n", step);
                                System.out.println(division);
                                System.out.println();
                            }
                            break;

                        default:
                            throw new IllegalArgumentException("Can't evaluate postfix expression due to invalid symbol " + postfixChar + " at position " + position + "\n" + postfix + "\n" + formatter.getOffsetSpaces(position-1) + "^");
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
