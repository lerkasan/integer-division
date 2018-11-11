package ua.com.foxminded.integerdivision;

import ua.com.foxminded.integerdivision.math.MathExpressionParser;
import ua.com.foxminded.integerdivision.text.factory.ClassicFormatterFactory;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;

import java.util.Scanner;

public class Main {

    public static final String EXIT_CMD = "exit";
    public static final String TOO_MANY_ARGUMENTS = "Too many arguments. Program support only one argument: -json or -classic";
    public static final String INTERACTIVE_REQUEST_FOR_INPUT = "\nInput a math expression: ";
    public static final String INTRODUCTION_APP_INFO = "\nEvaluating math expressions. Print 'exit' or press Ctrl + C or Ctrl + D to quit.\n" +
            "You can run this application with commandline parameter -json to get result output in json \n" +
            "or with commandline parameter -classic (or without any parameter) to get detailed result that looks like at school. ";

    public static void main(String[] args) {
        FormatterFactory formatterFactory = new ClassicFormatterFactory();
        if (args.length > 1) {
            throw new IllegalArgumentException(TOO_MANY_ARGUMENTS);
        }
        if (args.length == 1) {
            String formatType = args[0];
            if (args[0].startsWith("-")) {
                formatType = args[0].substring(1);
            }
            formatterFactory = FormatterFactory.getFormatterFactory(formatType);
        }
        if (args.length == 0) {
            formatterFactory = FormatterFactory.getClassicFormatterFactory();
        }

        String expression;
        Scanner in = new Scanner(System.in);
        System.out.println(INTRODUCTION_APP_INFO);
        do {
            System.out.print(INTERACTIVE_REQUEST_FOR_INPUT);
            expression = in.nextLine();
            if (!EXIT_CMD.equals(expression)) {
                try {
                    MathExpressionParser mathParser = new MathExpressionParser();
                    boolean verbose = true;
                    System.out.println(expression + " = " + mathParser.evaluate(expression, formatterFactory, verbose));
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!EXIT_CMD.equalsIgnoreCase(expression));
    }
}
