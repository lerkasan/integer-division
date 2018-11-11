package ua.com.foxminded.integerdivision.text.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.math.operation.Result;

public abstract class Formatter {

    public static final String NULL_ARGUMENT_MESSAGE = "Operation parameter passed to Formatter is null.";
    protected static final String WRONG_OPERATION_TYPE = "Wrong operation type.";
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal output format type.";

    protected String outputType;

    public static String getRepeatingSymbols(String symbols, int repeatAmount) {
        StringBuilder repeatingSymbols = new StringBuilder();
        for (int i = 0; i < repeatAmount; i++) {
            repeatingSymbols.append(symbols);
        }
        return  repeatingSymbols.toString();
    }

    public static String getOffsetSpaces(int offset) {
        return getRepeatingSymbols(" ", offset);
    }

    public String getLine(int length) {
        return getRepeatingSymbols("-", length);
    }

    protected abstract String formatClassicOutput(Operation operation);

    public String formatOutput(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        switch (outputType.toLowerCase()) {
            case "classic":
                return formatClassicOutput(operation);
            case "json":
                return toJson(operation.getResult());
            default:
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
    }

    public static String toJson(Result result) {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            System.out.println("Technical details: ");
            e.printStackTrace();
        }
        return json;
    }
}
