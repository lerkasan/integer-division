package ua.com.foxminded.integerdivision.text.formatter;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.math.operation.addition.IntegerAddition;

import java.math.BigInteger;

public class AdditionFormatter extends Formatter {

    private static char operationSign = '+';

    public AdditionFormatter() {
        this.operationSign = '+';
    }

    public AdditionFormatter(String outputType) {
        this.operationSign = '+';
        this.outputType = outputType;
    }

    public AdditionFormatter(String outputType, char operationSign) {
        this.outputType = outputType;
        this.operationSign = operationSign;
    }

    protected String formatClassicOutput(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (!(operation instanceof IntegerAddition)) {
            throw new IllegalArgumentException(WRONG_OPERATION_TYPE);
        }
        IntegerAddition addition = (IntegerAddition) operation;
        String result;
        String sum = addition.getNumericResult().toString();
        if (operationSign == '-') {
            addition.setSecondAddend(BigInteger.valueOf(-1).multiply(addition.getSecondAddend()));
        }
        int firstAddendLength = addition.getFirstAddend().toString().length();
        int secondAddendLength = addition.getSecondAddend().toString().length();
        int firstAddendOffset = sum.length() - firstAddendLength;
        int secondAddendOffset = sum.length() - secondAddendLength;
        int maxLengthOfAddend = firstAddendLength >= secondAddendLength ? firstAddendLength : secondAddendLength;
        if (sum.length() >= maxLengthOfAddend) {
            result = getOffsetSpaces(firstAddendOffset + 2) + addition.getFirstAddend() + "\n"
                    + getOffsetSpaces(secondAddendOffset + 2) + addition.getSecondAddend() + "\n"
                    + operationSign + " " + getLine(sum.length()) + "\n"
                    + getOffsetSpaces(2) + sum;
        } else {
            firstAddendOffset = maxLengthOfAddend - firstAddendLength;
            secondAddendOffset = maxLengthOfAddend - secondAddendLength;
            result = getOffsetSpaces(firstAddendOffset + 2) + addition.getFirstAddend() + "\n"
                    + getOffsetSpaces(secondAddendOffset + 2) + addition.getSecondAddend() + "\n"
                    + operationSign + " " + getLine(maxLengthOfAddend) + "\n"
                    + getOffsetSpaces(maxLengthOfAddend - sum.length() + 2) + sum;
        }
        return result;
    }
}
