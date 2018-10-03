package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerDivision extends Operation {
    private static final String DIVISION_BY_ZERO_MESSAGE = "Can't divide by zero.";

    private BigInteger dividend;
    private BigInteger divisor;

    public IntegerDivision(BigInteger dividend, BigInteger divisor) {
        super(2,0, Arrays.asList(dividend, divisor));
        this.dividend = dividend;
        this.divisor = divisor;
    }

    private class DivisionResult extends Result {
        @JsonProperty
        private BigInteger quotient;

        @JsonProperty
        private BigInteger remainder;

        @JsonProperty
        private List<IntermediateDivisionResult> steps;

        private DivisionResult() {}

        private DivisionResult(BigInteger quotient, BigInteger remainder, List<IntermediateDivisionResult> steps) {
            this.quotient = quotient;
            this.remainder = remainder;
            this.steps = steps;
        }
    }

    private class IntermediateDivisionResult extends Result{
        @JsonProperty
        private BigInteger minuend;

        @JsonProperty
        private BigInteger subtrahend;

        @JsonProperty
        private BigInteger difference;

        @JsonProperty
        private BigInteger quotient;


        private IntermediateDivisionResult() {
            this.minuend = BigInteger.ZERO;
            this.subtrahend = BigInteger.ZERO;
            this.difference = BigInteger.ZERO;
            this.quotient = BigInteger.ZERO;
            this.rearIndex = 0;
        }

        private IntermediateDivisionResult(BigInteger minuend, BigInteger subtrahend, BigInteger difference, BigInteger quotient, int rearIndex) {
            this.minuend = minuend;
            this.subtrahend = subtrahend;
            this.difference = difference;
            this.quotient = quotient;
            this.rearIndex = rearIndex;
        }
    }

    private BigInteger findFirstDigits(BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        String firstDigits = String.valueOf(absoluteNumber).substring(0, index + 1);
        return new BigInteger(firstDigits);
    }

    @Override
    public DivisionResult calculate() {
        if ((dividend == null) || (divisor == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (divisor.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException(DIVISION_BY_ZERO_MESSAGE);
        }
        if (dividend.equals(BigInteger.ZERO)) {
            return new DivisionResult(BigInteger.ZERO, BigInteger.ZERO, Arrays.asList(new IntermediateDivisionResult()));
        }
        BigInteger absoluteDividend = dividend.abs();
        BigInteger absoluteDivisor = divisor.abs();
        int dividendLength = absoluteDividend.toString().length();
        if (absoluteDividend.compareTo(absoluteDivisor) < 0) {
            List<IntermediateDivisionResult> steps =
                    Arrays.asList(new IntermediateDivisionResult(dividend, BigInteger.ZERO, dividend, BigInteger.ZERO, dividendLength - 1));
            return new DivisionResult(BigInteger.ZERO, dividend, steps);
        }
        if (dividend.compareTo(divisor) == 0) {
            List<IntermediateDivisionResult> steps =
                    Arrays.asList(new IntermediateDivisionResult(dividend, divisor, BigInteger.ZERO, BigInteger.ONE, dividendLength - 1));
            return new DivisionResult(BigInteger.ONE, BigInteger.ZERO, steps);
        }
        BigInteger previousRemainder = BigInteger.ZERO;
        int divisorLength = absoluteDivisor.toString().length();
        int currentDividendDigitRearIndex = divisorLength - 1;
        List<IntermediateDivisionResult> stepResults = new ArrayList<>();
        DivisionResult result = new DivisionResult();
        do {
            IntermediateDivisionResult stepResult = new IntermediateDivisionResult();
            if ((BigInteger.ZERO.equals(previousRemainder)) && (currentDividendDigitRearIndex == divisorLength - 1)) { // first step
                stepResult.minuend = findFirstDigits(absoluteDividend, currentDividendDigitRearIndex);
                stepResult.difference = stepResult.minuend;
            } else {
                stepResult.difference = previousRemainder;
                stepResult.minuend = stepResult.difference;
            }
            if ((absoluteDivisor.compareTo(stepResult.minuend) >= 0) && (currentDividendDigitRearIndex < dividendLength - 1)) {
                currentDividendDigitRearIndex++;
                int nextDigitInDividend = findDigitAtIndex(absoluteDividend, currentDividendDigitRearIndex);
                stepResult.minuend = BigInteger.TEN.multiply(stepResult.difference).add(BigInteger.valueOf(nextDigitInDividend));
            }
            stepResult.quotient = stepResult.minuend.divide(absoluteDivisor);
            stepResult.subtrahend = stepResult.quotient.multiply(absoluteDivisor);
            stepResult.difference = stepResult.minuend.subtract(stepResult.subtrahend);
            stepResult.rearIndex = currentDividendDigitRearIndex;
            previousRemainder = stepResult.difference;
            stepResults.add(stepResult);
        } while (currentDividendDigitRearIndex < dividendLength - 1);
        boolean isResultNegativeInteger = (BigInteger.ZERO.compareTo(dividend) > 0) ^ (BigInteger.ZERO.compareTo(divisor) > 0);
        if (isResultNegativeInteger) {
            stepResults.get(0).quotient = BigInteger.valueOf(-1).multiply(stepResults.get(0).quotient);
        }
        if (BigInteger.ZERO.compareTo(dividend) > 0) {
           result.remainder = BigInteger.valueOf(-1).multiply(stepResults.get(stepResults.size() - 1).difference);
           stepResults.get(stepResults.size() - 1).difference = result.remainder;
        }
        BigInteger wholeQuotient = calculateWholeQuotient(stepResults);
        return new DivisionResult(wholeQuotient, result.remainder, stepResults);
    }

    private BigInteger calculateWholeQuotient(List<IntermediateDivisionResult> stepResults) {
        if (null == stepResults) {
            throw new IllegalArgumentException(EMPTY_RESULTS_MESSAGE);
        }
        BigInteger quotient = BigInteger.ZERO;
        for (IntermediateDivisionResult step : stepResults) {
            quotient = BigInteger.TEN.multiply(quotient).add(step.quotient.abs());
        }
        if (BigInteger.ZERO.compareTo(stepResults.get(0).quotient) > 0) {
            quotient = BigInteger.valueOf(-1).multiply(quotient);
        }
        return quotient;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        StringBuilder resultToPrint = new StringBuilder();
        DivisionResult result = calculate();
        List<IntermediateDivisionResult> stepResults = result.steps;
        BigInteger absoluteDividend = dividend.abs();
        int absoluteDividendLength = absoluteDividend.toString().length();
        System.out.println();
        resultToPrint.append("_" + dividend + " | " + divisor + "\n");
        int stepCounter = 0;
        for (IntermediateDivisionResult step : stepResults) {
            int minuendLength = String.valueOf(step.minuend).length();
            int subtrahendLength = String.valueOf(step.subtrahend).length();
            int remainderLength = String.valueOf(step.difference).length();
            int offsetMinuend = step.rearIndex - minuendLength + 1;
            int offsetSubtrahend = step.rearIndex - subtrahendLength + 1;
            int offsetRemainder = offsetMinuend +  minuendLength - remainderLength + 1;
            String additionalOffsetForVertical = "";
            if (BigInteger.ZERO.compareTo(dividend) > 0) {
                offsetMinuend++;
                offsetSubtrahend++;
                offsetRemainder++;
                additionalOffsetForVertical = " ";
            }
            if ((stepCounter > 0) && (!BigInteger.ZERO.equals(step.subtrahend))) {
                resultToPrint.append(formatter.getOffsetSpaces(offsetMinuend) + "_" + step.minuend + "\n");
            }
            if ((!BigInteger.ZERO.equals(step.subtrahend)) || (stepResults.size() == 1)) {
                if (stepCounter == 0) {
                    BigInteger quotient = calculateWholeQuotient(stepResults);
                    int quotientLength = String.valueOf(quotient).length();
                    resultToPrint.append(formatter.getOffsetSpaces(offsetSubtrahend + 1) + step.subtrahend
                            + formatter.getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1));
                    resultToPrint.append(additionalOffsetForVertical + "|" + formatter.getLine(quotientLength + 2) + "\n");
                    resultToPrint.append(formatter.getOffsetSpaces(offsetSubtrahend + 1) + formatter.getLine(subtrahendLength)
                            + formatter.getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1)
                            + additionalOffsetForVertical + "| " + quotient + "\n");
                } else {
                    resultToPrint.append(formatter.getOffsetSpaces(offsetSubtrahend + 1) + step.subtrahend);
                    resultToPrint.append("\n" + formatter.getOffsetSpaces(offsetSubtrahend + 1) + formatter.getLine(subtrahendLength) + "\n");
                }
            }
            stepCounter++;
            if (stepCounter == stepResults.size()) {
                resultToPrint.append(formatter.getOffsetSpaces(offsetRemainder) + step.difference);
            }
        }
        return resultToPrint.toString();
    }
}
