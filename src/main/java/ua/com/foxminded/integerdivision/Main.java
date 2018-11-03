package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String expression = "";
        Scanner in = new Scanner(System.in);
        System.out.println("\nEvaluating math expressions. Print 'exit' or press Ctrl + C or Ctrl + D to quit.");
        do {
            System.out.print("\nInput a math expression: ");
            expression = in.nextLine();
            if (!"exit".equals(expression)) {
                try {
                    MathExpressionParser mathParser = new MathExpressionParser();
                    boolean verbose = true;
                    System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!"exit".equals(expression));

        IntegerDivision division = new IntegerDivision(BigInteger.valueOf(48), BigInteger.valueOf(4));
        System.out.println(division.toString());

        IntegerSubtraction substraction = new IntegerSubtraction(new BigInteger("14"), new BigInteger("16"));
        System.out.println(substraction);

        substraction = new IntegerSubtraction(new BigInteger("8"), new BigInteger("9"));
        System.out.println(substraction);

        IntegerAddition addition = new IntegerAddition(new BigInteger("22"), new BigInteger("-14"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("-14"), new BigInteger("16"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("8"), new BigInteger("-9"));
        System.out.println(addition);

        expression = "-5+3*((0-4)+16)";
        MathExpressionParser mathParser = new MathExpressionParser();
        System.out.println("Postfix is: " + mathParser.convertInfixToPostfix(expression));
        boolean verbose = true;
        System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
        System.out.println();

        expression = "2142156 / 435";
        System.out.println("Postfix is: " + mathParser.convertInfixToPostfix(expression));
        System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
        System.out.println();

        expression = "98 + 7 * 2-36/4 + 3*(-4+16)";
        System.out.println("Postfix is: " + mathParser.convertInfixToPostfix(expression));
        System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
        System.out.println();

        expression = "98 + 7 * -2-36/4 + 3*(-4+16)";
        System.out.println("Postfix is: " + mathParser.convertInfixToPostfix(expression));
        System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
        System.out.println();

        expression = "-25-(98-4)/-2 + 7 * -2-36/4 + 3*(-4+16)";
        System.out.println("Postfix is: " + mathParser.convertInfixToPostfix(expression));
        System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
        System.out.println();

        System.out.println(mathParser.convertInfixToPostfix("-14+16"));
        System.out.println(mathParser.evaluate("-14+16", verbose));
        System.out.println();
        System.out.println(mathParser.convertInfixToPostfix("5+7-4"));
        System.out.println(mathParser.evaluate("5+7-4", verbose));
        System.out.println();
        System.out.println(mathParser.convertInfixToPostfix("5+7*4"));
        System.out.println(mathParser.evaluate("5+7*4", verbose));
        System.out.println();
        System.out.println(mathParser.convertInfixToPostfix("5*(7+4)"));
        System.out.println(mathParser.evaluate("5*(7+4)", verbose));
        System.out.println();
        System.out.println(mathParser.convertInfixToPostfix("15*(2+3)-44/(5+6)"));
        System.out.println(mathParser.evaluate("15*(2+3)-44/(5+6)", verbose));
        System.out.println();

        IntegerMultiplication multiplication = new IntegerMultiplication(new BigInteger("2685"), new BigInteger("17"));
        System.out.println(multiplication);
        try {
            System.out.println(multiplication.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        multiplication = new IntegerMultiplication(new BigInteger("2685043"), new BigInteger("176"));
        System.out.println(multiplication);
        try {
            System.out.println(multiplication.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        multiplication = new IntegerMultiplication(new BigInteger("2685043"), new BigInteger("-176"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("2685043"), new BigInteger("-1"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("2685043"), new BigInteger("1"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("-2685043"), new BigInteger("176"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("7296346853567465465462368220543"), new BigInteger("-173683783768489214460"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("-176"), new BigInteger("-2685043"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("176"), new BigInteger("2685043"));
        System.out.println(multiplication);
        try {
            System.out.println(multiplication.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        multiplication = new IntegerMultiplication(new BigInteger("-2685043"), new BigInteger("176"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("2685"), new BigInteger("-17"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("-2685"), new BigInteger("-17"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("0"), new BigInteger("-17"));
        System.out.println(multiplication);

        multiplication = new IntegerMultiplication(new BigInteger("-2685"), new BigInteger("0"));
        System.out.println(multiplication);

        substraction = new IntegerSubtraction(new BigInteger("-876131345924584627"), new BigInteger("-75491249882053"));
        System.out.println(substraction);

        substraction = new IntegerSubtraction(new BigInteger("-876131345924584627"), new BigInteger("75491249882053"));
        System.out.println(substraction);

        substraction = new IntegerSubtraction(new BigInteger("14"), new BigInteger("16"));
        System.out.println(substraction);


        try {
            System.out.println(substraction.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        substraction = new IntegerSubtraction(new BigInteger("876131345924584627"), new BigInteger("75491249882053"));
        System.out.println(substraction);

        substraction = new IntegerSubtraction(new BigInteger("876131345924584627"), new BigInteger("-75491249882053"));
        System.out.println(substraction);

        System.out.println("Addition");

        addition = new IntegerAddition(new BigInteger("-876131345924584627"), new BigInteger("-75491249882053"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("18795"), new BigInteger("26850"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("876131345924584627"), new BigInteger("-75491249882053"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("-876131345924584627"), new BigInteger("75491249882053"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("876131345924584627"), new BigInteger("75491249882053"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("-876131345924584627"), new BigInteger("0"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("0"), new BigInteger("-75491249882053"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("0"), new BigInteger("0"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("923"), new BigInteger("21123"));
        System.out.println(addition);

        System.out.println("Negative numbers");
        addition = new IntegerAddition(new BigInteger("-123"), new BigInteger("123"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("123"), new BigInteger("-123"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("0"), new BigInteger("-123"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("-123"), new BigInteger("0"));
        System.out.println(addition);

        addition = new IntegerAddition(new BigInteger("-123"), new BigInteger("-123"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("57425"), new BigInteger("-89"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("-89"), new BigInteger("57425"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("89"), new BigInteger("-57425"));
        System.out.println(addition);
        addition = new IntegerAddition(new BigInteger("-57425"), new BigInteger("89"));
        System.out.println(addition);

//        Scanner in = new Scanner(System.in);
//        System.out.println("\nDividing integers. Print 'exit' or press Ctrl + C or Ctrl + D to quit.");
//        String dividend = "";
//        String divisor = "";
//        do {
//            System.out.print("\nInput a dividend: ");
//            dividend = in.next();
//            if (!"exit".equals(dividend)) {
//                System.out.print("\nInput a divisor: ");
//                divisor = in.next();
//                if (!"exit".equals(divisor)) {
//                    try {
//                        division = new IntegerDivision(new BigInteger(dividend), new BigInteger(divisor));
//                        System.out.println(division.toString());
//                    } catch (NumberFormatException e) {
//                        System.out.println("You entered not a numeric dividend or divisor.");
//                    }
//                }
//            }
//        } while (!"exit".equals(dividend) && !"exit".equals(divisor));

        division = new IntegerDivision(BigInteger.ZERO, BigInteger.valueOf(14));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-78), BigInteger.valueOf(14));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-1234), BigInteger.valueOf(123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(123457), BigInteger.valueOf(123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(235), BigInteger.valueOf(972));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(9));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(23), BigInteger.valueOf(14));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(9), BigInteger.valueOf(6));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-234), BigInteger.valueOf(12));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(23457), BigInteger.valueOf(123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(1469236343), BigInteger.valueOf(128));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(673));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(6249));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(73));
        System.out.println(division.toString());
        division = new IntegerDivision(new BigInteger("063782071731462398534"), new BigInteger("7521654673"));
        System.out.println(division.toString());
        division = new IntegerDivision(new BigInteger("34320968763782071731462398534"), new BigInteger("0193563"));
        System.out.println(division.toString());
        division = new IntegerDivision(new BigInteger("7390934320968763782071731462398534"), BigInteger.valueOf(96));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.ZERO, BigInteger.valueOf(14));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(14), BigInteger.valueOf(14));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-23457), BigInteger.valueOf(123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(23457), BigInteger.valueOf(-123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-23457), BigInteger.valueOf(-123));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(4257583), BigInteger.valueOf(-812));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-4257583), BigInteger.valueOf(812));
        System.out.println(division.toString());
        division = new IntegerDivision(BigInteger.valueOf(-4257583), BigInteger.valueOf(-812));
        System.out.println(division.toString());
        try {
            System.out.println(division.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
