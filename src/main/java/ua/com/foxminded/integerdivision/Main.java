package ua.com.foxminded.integerdivision;

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
    }
}
