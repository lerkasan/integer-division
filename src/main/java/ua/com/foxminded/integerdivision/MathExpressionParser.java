package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class MathExpressionParser {

    public String convertInfixToPostfix(String infix) {
        if (infix == null) {
            throw new IllegalArgumentException("Null infix string can't be converted to postfix.");
        }
        String postfix = "";
        char[] infixChars = infix.toCharArray();
        Deque<Character> operatorStack = new ArrayDeque<>();
        for (char infixChar : infixChars) {
            if (Character.isLetter(infixChar)) {
                throw new IllegalArgumentException("Letters are not allowed in arithmetic expressions.");
            }
            if (Character.isDigit(infixChar)) {
                postfix += infixChar;
            } else {
                char operatorFromStack;
                switch (infixChar) {
                    case '(':
                        operatorStack.push(infixChar);
                        break;

                    case ')':
                        while (!operatorStack.isEmpty()) {
                            operatorFromStack = operatorStack.pop();
                            if (operatorFromStack != '(') {
                                postfix = postfix.trim() + " " + operatorFromStack + " ";
                            }
                        }
                        break;

                    case '+':
                    case '-':
                        if (postfix == "") {
                            postfix = "0";
                        }
                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
                        break;
                    case '*':
                    case '/':
                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
                        break;

//                    case '+':
//                    case '-':
//                    case '*':
//                    case '/':
//                        postfix = postfix.trim() + " " + prioritizeStackOperators(operatorStack, infixChar);
//                        break;

                    case ' ':
                        break;

                    case '.':
                    case ',':
                        throw new IllegalArgumentException("Only integer numbers are allowed in the expression.");

                    default:
                        throw new IllegalArgumentException("Expression contains some invalid characters.");
                }
            }
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
        String operand = "";
        for (char postfixchar : postfixChars) {
            if (Character.isDigit(postfixchar)) {
                operand += postfixchar;
            } else {
                if (operand != "") {
                    operandStack.push(operand);
                    operand = "";
                }
                if (postfixchar != ' ') {
//                    if ((operandStack.size() < 2) && ((postfixchar == '-') || (postfixchar == '+'))) {
//                        operand = "" + postfixchar;
//                        continue;
//                    }

//                    if (operandStack.size() >= 2) {
                        String secondOperand = operandStack.pop();
                        String firstOperand = operandStack.pop();
//                    }
                    String result = "";
                    switch (postfixchar) {
                        case '+':
                            IntegerAddition addition = new IntegerAddition(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = addition.getResult();
                            if (verbose) {
                                System.out.println(addition);
                                System.out.println();
                            }
                            break;
                        case '-':
                            IntegerSubstraction substraction = new IntegerSubstraction(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = substraction.getResult();
                            if (verbose) {
                                System.out.println(substraction);
                                System.out.println();
                            }
                            break;
                        case '*':
                            IntegerMultiplication multiplication = new IntegerMultiplication(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = multiplication.getProduct();
                            if (verbose) {
                                System.out.println(multiplication);
                                System.out.println();
                            }
                            break;

                        case '/':
                            IntegerDivision division = new IntegerDivision(new BigInteger(firstOperand), new BigInteger(secondOperand));
                            result = division.getQuotient();
                            if (verbose) {
                                System.out.println(division);
                                System.out.println();
                            }
                            break;

                        default:
                            throw new IllegalArgumentException("Invalid symbol in postfix expression. Can't evaluate.");
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
