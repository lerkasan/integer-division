package ua.com.foxminded.integerdivision.text;

import ua.com.foxminded.integerdivision.math.Operation;

import java.math.BigInteger;

// TODO lookin on this class I would say it's a way too small, and there's nothing surprising about that coz all the hardcore formatting is in the operations
public class Formatter {

    protected static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";

    public String getRepeatingSymbols(String symbols, int repeatAmount) {
        StringBuilder repeatingSymbols = new StringBuilder();
        for (int i = 0; i < repeatAmount; i++) {
            repeatingSymbols.append(symbols);
        }
        return  repeatingSymbols.toString();
    }

    public String getOffsetSpaces(int offset) {
        return getRepeatingSymbols(" ", offset);
    }

    public String getLine(int length) {
        return getRepeatingSymbols("-", length);
    }

    public String deleteLeadingZeros(String number) {
        while (number.startsWith("0") && number.length() > 1) {
            number = number.substring(1);
        }
        return number;
    }

    protected void checkIndexRange(BigInteger number, int index) {
        if (number == null) {
            throw new IllegalArgumentException(Operation.NULL_ARGUMENT_MESSAGE);
        }
        BigInteger absoluteNumber = number.abs();
        int numberLength = String.valueOf(absoluteNumber).length();
        if ((index >= numberLength) || (index < 0)) {
            throw new IllegalArgumentException(ILLEGAL_INDEX_MESSAGE);
        }
    }

    // TODO probably this method shouldn't be here
    protected int findDigitAtIndex(BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        char charAtIndex = String.valueOf(absoluteNumber).charAt(index);
        return Integer.valueOf(String.valueOf(charAtIndex));
    }
}
