package ua.com.foxminded.integerdivision.text.formatter;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.math.operation.multiplication.IntegerMultiplication;
import ua.com.foxminded.integerdivision.math.operation.multiplication.IntermediateMultiplicationResult;

import java.math.BigInteger;

public class MultiplicationFormatter extends Formatter {

    public MultiplicationFormatter(String outputType) {
        this.outputType = outputType;
    }

    protected String formatClassicOutput(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (!(operation instanceof IntegerMultiplication)) {
            throw new IllegalArgumentException(WRONG_OPERATION_TYPE);
        }
        IntegerMultiplication multiplication = (IntegerMultiplication) operation;
        int multiplicandLength = multiplication.getMultiplicand().toString().length();
        int multiplierLength = multiplication.getMultiplier().toString().length();
        int productLength = multiplication.getNumericResult().toString().length();
        int operandMaxLength = multiplierLength > multiplicandLength ? multiplierLength : multiplicandLength;
        int maxLength = operandMaxLength > productLength ? operandMaxLength : productLength;
        int multiplicandOffset = maxLength - multiplicandLength;
        int multiplierOffset = maxLength - multiplierLength;
        int productOffset = maxLength - productLength;
        StringBuilder output = new StringBuilder();
        output.append(getOffsetSpaces(multiplicandOffset + 2) + multiplication.getMultiplicand().toString() + "\n"
                + getOffsetSpaces(multiplierOffset + 2) + multiplication.getMultiplier().toString() + "\n"
                + getOffsetSpaces(maxLength - operandMaxLength) + "* " + getLine(operandMaxLength) + "\n");
        if (!BigInteger.ZERO.equals(multiplication.getNumericResult()) && (multiplication.getResult().getSteps().size() > 1)) {
            for (IntermediateMultiplicationResult step : multiplication.getResult().getSteps()) {
                int addendOffset = maxLength - step.getAddend().length();
                if (!BigInteger.ZERO.equals(new BigInteger(step.getAddend()))) {
                    output.append(getOffsetSpaces(addendOffset - step.getRearIndex() + 2) + step.getAddend() + "\n");
                }
            }
            output.append("+ " + getOffsetSpaces(productOffset) + getLine(maxLength) + "\n");
        }
        output.append(getOffsetSpaces(productOffset + 2) + multiplication.getNumericResult());
        return output.toString();
    }
}
