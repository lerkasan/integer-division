package ua.com.foxminded.integerdivision.math.operation;

import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import java.math.BigInteger;
import java.util.List;

public abstract class Operation {
    protected static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    public static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";
    private static final String OPERANDS_MISMATCH_MESSAGE = "Amount of operands doesn't match operation parity,";
    public static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";

    private int arity;
    private int priority;
    private List<BigInteger> operands;

    protected Operation() {}

    protected Operation(int arity, int priority, List<BigInteger> operands) {
        if (operands == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (arity != operands.size()) {
            throw new IllegalArgumentException(OPERANDS_MISMATCH_MESSAGE);
        }
        this.arity = arity;
        this.priority = priority;
        this.operands = operands;
    }

    public abstract Result getResult();

    protected abstract BigInteger getNumericResult();

    protected abstract Result calculate();

    public int findDigitAtIndex(BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        char charAtIndex = String.valueOf(absoluteNumber).charAt(index);
        return Integer.valueOf(String.valueOf(charAtIndex));
    }

    public void checkIndexRange(BigInteger number, int index) {
        if (number == null) {
            throw new IllegalArgumentException(Operation.NULL_ARGUMENT_MESSAGE);
        }
        BigInteger absoluteNumber = number.abs();
        int numberLength = String.valueOf(absoluteNumber).length();
        if ((index >= numberLength) || (index < 0)) {
            throw new IllegalArgumentException(ILLEGAL_INDEX_MESSAGE);
        }
    }

    protected String deleteLeadingZeros(String number) {
        while (number.startsWith("0") && number.length() > 1) {
            number = number.substring(1);
        }
        return number;
    }

    public String toJson() {
        return Formatter.toJson(getResult());
    }
}
