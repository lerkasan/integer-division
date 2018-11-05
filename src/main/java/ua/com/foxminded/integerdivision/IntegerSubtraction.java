package ua.com.foxminded.integerdivision;

import java.math.BigInteger;

public class IntegerSubtraction extends IntegerAddition {

    public IntegerSubtraction(BigInteger minuend, BigInteger subtrahend) {
//        if ((minuend == null) || (subtrahend == null)) {
//            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
//        }
        super(minuend, BigInteger.valueOf(-1).multiply(subtrahend));
    }

    @Override
    public String toString() {
        return formatOutput('-');
    }
}
