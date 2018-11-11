package ua.com.foxminded.integerdivision.text.formatter;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.math.operation.division.IntegerDivision;
import ua.com.foxminded.integerdivision.math.operation.division.IntermediateDivisionResult;

import java.math.BigInteger;
import java.util.List;

public class DivisionFormatter extends Formatter {

    public DivisionFormatter(String outputType) {
        this.outputType = outputType;
    }

    protected String formatClassicOutput(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (!(operation instanceof IntegerDivision)) {
            throw new IllegalArgumentException(WRONG_OPERATION_TYPE);
        }
        IntegerDivision division = (IntegerDivision) operation;
        StringBuilder resultToPrint = new StringBuilder();
        List<IntermediateDivisionResult> stepResults = division.getResult().getSteps();
        BigInteger absoluteDividend = division.getDividend().abs();
        int absoluteDividendLength = absoluteDividend.toString().length();
        resultToPrint.append("_" + division.getDividend() + " | " + division.getDivisor() + "\n");
        int stepCounter = 0;
        for (IntermediateDivisionResult step : stepResults) {
            int minuendLength = String.valueOf(step.getMinuend()).length();
            int subtrahendLength = String.valueOf(step.getSubtrahend()).length();
            int remainderLength = String.valueOf(step.getDifference()).length();
            int offsetMinuend = step.getRearIndex() - minuendLength + 1;
            int offsetSubtrahend = step.getRearIndex() - subtrahendLength + 1;
            int offsetRemainder = offsetMinuend +  minuendLength - remainderLength + 1;
            String additionalOffsetForVertical = "";
            if (BigInteger.ZERO.compareTo(division.getDividend()) > 0) {
                offsetMinuend++;
                offsetSubtrahend++;
                offsetRemainder++;
                additionalOffsetForVertical = " ";
            }
            if ((stepCounter > 0) && (!BigInteger.ZERO.equals(step.getSubtrahend()))) {
                resultToPrint.append(getOffsetSpaces(offsetMinuend) + "_" + step.getMinuend() + "\n");
            }
            if ((!BigInteger.ZERO.equals(step.getSubtrahend())) || (stepResults.size() == 1)) {
                if (stepCounter == 0) {
                    BigInteger quotient = division.getNumericResult();
                    int quotientLength = String.valueOf(quotient).length();
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + step.getSubtrahend()
                            + getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1));
                    resultToPrint.append(additionalOffsetForVertical + "|" + getLine(quotientLength + 2) + "\n");
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + getLine(subtrahendLength)
                            + getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1)
                            + additionalOffsetForVertical + "| " + quotient + "\n");
                } else {
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + step.getSubtrahend());
                    resultToPrint.append("\n" + getOffsetSpaces(offsetSubtrahend + 1) + getLine(subtrahendLength) + "\n");
                }
            }
            stepCounter++;
            if (stepCounter == stepResults.size()) {
                resultToPrint.append(getOffsetSpaces(offsetRemainder) + step.getDifference());
            }
        }
        return resultToPrint.toString();
    }
}
