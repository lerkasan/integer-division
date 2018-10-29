package ua.com.foxminded.integerdivision;

import java.math.BigInteger;

public class IntegerSubtraction extends IntegerAddition {

    public IntegerSubtraction(BigInteger minuend, BigInteger subtrahend) {
        super(minuend, BigInteger.valueOf(-1).multiply(subtrahend));
    }

    public AdditionResult calculate() {
        return super.calculate();
    }

    public String toString() {
        String result = formatOutput('-');
        System.out.println();
        return result;
    }
}
