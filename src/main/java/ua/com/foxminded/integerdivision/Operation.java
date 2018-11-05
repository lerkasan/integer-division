package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.math.BigInteger;
import java.util.List;

abstract class Operation {
    protected static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    protected static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";
    private static final String OPERANDS_MISMATCH_MESSAGE = "Amount of operands doesn't match operation parity,";

    private int arity;
    private int priority;
    private List<BigInteger> operands;

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

    protected abstract Result calculate();

    protected abstract String getResult();

    public abstract String toString();

    public String toJson() {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Result result = calculate();
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}