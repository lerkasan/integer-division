package ua.com.foxminded.integerdivision;

import java.math.BigInteger;

public class IntegerSubtraction extends IntegerAddition {

    public IntegerSubtraction(BigInteger minuend, BigInteger subtrahend) {
//        if ((minuend == null) || (subtrahend == null)) {
//            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
//        }
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
