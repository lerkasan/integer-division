package ua.com.foxminded.integerdivision;

import ua.com.foxminded.integerdivision.math.MathExpressionParser;

import java.util.Scanner;

public class Main {

    public static final String EXIT_CMD = "exit";

    public static void main(String[] args) {
        String expression;
        Scanner in = new Scanner(System.in);
        System.out.println("\nEvaluating math expressions. Print 'exit' or press Ctrl + C or Ctrl + D to quit.");

        do {
            System.out.print("\nInput a math expression: ");
            expression = in.nextLine();
            if (!EXIT_CMD.equals(expression)) {
                try {
                    MathExpressionParser mathParser = new MathExpressionParser();
                    boolean verbose = true;
                    System.out.println(expression + " = " + mathParser.evaluate(expression, verbose));
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    /*System.out.println( TODO
                        ...user friendly message ...
                        Technical details:
                    );/*/
                    e.printStackTrace();
                }
            }
        } while (!EXIT_CMD.equalsIgnoreCase(expression));
    }
}
