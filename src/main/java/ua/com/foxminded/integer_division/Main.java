package ua.com.foxminded.integer_division;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("\nDividing integers. Print 'exit' or press Ctrl + C or Ctrl + D to quit.");
        String dividend = "";
        String divisor = "";
        IntegerDivision division = new IntegerDivision();
        do {
            System.out.print("\nInput a dividend: ");
            dividend = in.next();
            if (!"exit".equals(dividend)) {
                System.out.print("\nInput a divisor: ");
                divisor = in.next();
                if (!"exit".equals(divisor)) {
                    try {
                        System.out.println(division.printLongDivision(new BigInteger(dividend), new BigInteger(divisor)));
                    } catch (NumberFormatException e) {
                        System.out.println("You entered not a numeric dividend or divisor.");
                    }
                }
            }
        } while (!"exit".equals(dividend) && !"exit".equals(divisor));

        System.out.println(division.printLongDivision(BigInteger.ZERO, BigInteger.valueOf(14)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-78), BigInteger.valueOf(78)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-1234), BigInteger.valueOf(123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(123457), BigInteger.valueOf(123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(235), BigInteger.valueOf(972)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(9)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(23), BigInteger.valueOf(14)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(9), BigInteger.valueOf(6)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-234), BigInteger.valueOf(12)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(23457), BigInteger.valueOf(123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(1469236343), BigInteger.valueOf(128)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(673)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(6249)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(2071462334), BigInteger.valueOf(73)));
        System.out.println(division.printLongDivision(new BigInteger("063782071731462398534"), new BigInteger("7521654673")));
        System.out.println(division.printLongDivision(new BigInteger("34320968763782071731462398534"), new BigInteger("0193563")));
        System.out.println(division.printLongDivision(new BigInteger("7390934320968763782071731462398534"), new BigInteger("96")));
        System.out.println(division.printLongDivision(BigInteger.ZERO, BigInteger.valueOf(14)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(14), BigInteger.valueOf(14)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-23457), BigInteger.valueOf(123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(23457), BigInteger.valueOf(-123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-23457), BigInteger.valueOf(-123)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(4257583), BigInteger.valueOf(-812)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-4257583), BigInteger.valueOf(812)));
        System.out.println(division.printLongDivision(BigInteger.valueOf(-4257583), BigInteger.valueOf(-812)));
    }
}
