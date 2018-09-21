package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.List;

abstract class Operation {
    protected static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";
    protected static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    protected static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";
    protected static final String OPERANDS_MISMATCH_MESSAGE = "Amount of operands doesn't match operation parity,";

    private int arity;
    private int priority;
    protected List<BigInteger> operands;

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

    abstract Result calculate();

    public abstract String toString();

    protected class Result {
    }
}