package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.math.BigInteger;
import java.util.List;

abstract class Operation {
    protected static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";
    protected static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    protected static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";
    protected static final String OPERANDS_MISMATCH_MESSAGE = "Amount of operands doesn't match operation parity,";

    private int arity;
    private int priority;
    private String result;

    public String getResult() {
        return result;
    }

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

    protected class Result {
        protected int rearIndex;
    }

    abstract protected Result calculate();

    public abstract String toString();

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Result result = calculate();
        return objectMapper.writeValueAsString(result);
    }

    protected void checkIndexRange(BigInteger number, int index) {
        BigInteger absoluteNumber = number.abs();
        int numberLength = String.valueOf(absoluteNumber).length();
        if ((index >= numberLength) || (index < 0)) {
            throw new IllegalArgumentException(Operation.ILLEGAL_INDEX_MESSAGE);
        }
    }

    protected int findDigitAtIndex(BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        char charAtIndex = String.valueOf(absoluteNumber).charAt(index);
        return Integer.valueOf(String.valueOf(charAtIndex));
    }
}