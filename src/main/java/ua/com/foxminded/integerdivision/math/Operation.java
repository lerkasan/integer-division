package ua.com.foxminded.integerdivision.math;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.math.BigInteger;
import java.util.List;

public abstract class Operation {
    protected static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    protected static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";
    private static final String OPERANDS_MISMATCH_MESSAGE = "Amount of operands doesn't match operation parity,";
    protected static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";

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

    protected abstract Result calculate();

    protected int findDigitAtIndex(BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        char charAtIndex = String.valueOf(absoluteNumber).charAt(index);
        return Integer.valueOf(String.valueOf(charAtIndex));
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

    protected abstract BigInteger getResult();

    public String toJson() {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Result result = calculate();
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            //my TODO User-friendly output
            e.printStackTrace();
        }
        return json;
    }
}