package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IntegerAddition addition = new IntegerAddition(new BigInteger("-876131345924584627"), new BigInteger("-75491249882053"));
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

        Scanner in = new Scanner(System.in);
        System.out.println("\nDividing integers. Print 'exit' or press Ctrl + C or Ctrl + D to quit.");
        String dividend = "";
        String divisor = "";
        do {
            System.out.print("\nInput a dividend: ");
            dividend = in.next();
            if (!"exit".equals(dividend)) {
                System.out.print("\nInput a divisor: ");
                divisor = in.next();
                if (!"exit".equals(divisor)) {
                    try {
                        IntegerDivision division = new IntegerDivision(new BigInteger(dividend), new BigInteger(divisor));
                        System.out.println(division.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("You entered not a numeric dividend or divisor.");
                    }
                }
            }
        } while (!"exit".equals(dividend) && !"exit".equals(divisor));

        IntegerDivision division = new IntegerDivision(BigInteger.ZERO, BigInteger.valueOf(14));
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
    }
}
