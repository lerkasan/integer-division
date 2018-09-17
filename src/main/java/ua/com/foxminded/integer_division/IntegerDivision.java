package ua.com.foxminded.integer_division;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerDivision {
    private static final String DIVISION_BY_ZERO_MESSAGE = "Can't divide by zero.";
    private static final String ILLEGAL_INDEX_MESSAGE = "Illegal index. Index should be in diapason from zero to number length minus one.";
    private static final String EMPTY_RESULTS_MESSAGE = "Empty result array.";
    private static final String NULL_ARGUMENT_MESSAGE = "Dividend or divisor is a null argument";

    private class IntermediateBigResult {
        private BigInteger minuend;
        private BigInteger subtrahend;
        private BigInteger remainder;
        private BigInteger quotient;
        private int rearIndex;

        private IntermediateBigResult() {
            this.minuend = BigInteger.ZERO;
            this.subtrahend = BigInteger.ZERO;
            this.remainder = BigInteger.ZERO;
            this.quotient = BigInteger.ZERO;
            this.rearIndex = 0;
        }

        private IntermediateBigResult(BigInteger minuend, BigInteger subtrahend, BigInteger remainder, BigInteger quotient, int rearIndex) {
            this.minuend = minuend;
            this.subtrahend = subtrahend;
            this.remainder = remainder;
            this.quotient = quotient;
            this.rearIndex = rearIndex;
        }
    }

    private void checkIndexRange(BigInteger number, int index) {
        BigInteger absoluteNumber = number.abs();
        int numberLength = String.valueOf(absoluteNumber).length();
        if ((index >= numberLength) || (index < 0)) {
            throw new IllegalArgumentException(ILLEGAL_INDEX_MESSAGE);
        }
    }

    private int findDigitAtIndex(BigInteger number, int index) {
        checkIndexRange(number,index);
        BigInteger absoluteNumber = number.abs();
        char charAtIndex = String.valueOf(absoluteNumber).charAt(index);
        return Integer.valueOf(String.valueOf(charAtIndex));
    }

    private BigInteger findFirstDigits (BigInteger number, int index) {
        checkIndexRange(number, index);
        BigInteger absoluteNumber = number.abs();
        String firstDigits = String.valueOf(absoluteNumber).substring(0, index + 1);
        return new BigInteger(firstDigits);
    }

    private List<IntermediateBigResult> performLongDivision(BigInteger dividend, BigInteger divisor) {
        if ((dividend == null) || (divisor == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (divisor.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException(DIVISION_BY_ZERO_MESSAGE);
        }
        if (dividend.equals(BigInteger.ZERO)) {
            return Arrays.asList(new IntermediateBigResult());
        }
        BigInteger absoluteDividend = dividend.abs();
        BigInteger absoluteDivisor = divisor.abs();
        int dividendLength = absoluteDividend.toString().length();
        if (absoluteDividend.compareTo(absoluteDivisor) < 0) {
            return Arrays.asList(new IntermediateBigResult(dividend, BigInteger.ZERO, dividend, BigInteger.ZERO, dividendLength - 1));
        }
        if (dividend.compareTo(divisor) == 0) {
            return Arrays.asList(new IntermediateBigResult(dividend, divisor, BigInteger.ZERO, BigInteger.ONE, dividendLength - 1));
        }
        BigInteger previousRemainder = BigInteger.ZERO;
        int divisorLength = absoluteDivisor.toString().length();
        int currentDividendDigitRearIndex = divisorLength - 1;
        List<IntermediateBigResult> stepResults = new ArrayList<>();
        do {
            IntermediateBigResult stepResult = new IntermediateBigResult();
            if ((BigInteger.ZERO.equals(previousRemainder)) && (currentDividendDigitRearIndex == divisorLength - 1)) { // first step
                stepResult.minuend = findFirstDigits(absoluteDividend, currentDividendDigitRearIndex);
                stepResult.remainder = stepResult.minuend;
            } else {
                stepResult.remainder = previousRemainder;
                stepResult.minuend = stepResult.remainder;
            }
            if ((absoluteDivisor.compareTo(stepResult.minuend) >= 0) && (currentDividendDigitRearIndex < dividendLength - 1)) {
                currentDividendDigitRearIndex++;
                int nextDigitInDividend = findDigitAtIndex(absoluteDividend, currentDividendDigitRearIndex);
                stepResult.minuend = BigInteger.TEN.multiply(stepResult.remainder).add(BigInteger.valueOf(nextDigitInDividend));
            }
            stepResult.quotient = stepResult.minuend.divide(absoluteDivisor);
            stepResult.subtrahend = stepResult.quotient.multiply(absoluteDivisor);
            stepResult.remainder = stepResult.minuend.subtract(stepResult.subtrahend);
            stepResult.rearIndex = currentDividendDigitRearIndex;
            previousRemainder = stepResult.remainder;
            stepResults.add(stepResult);
        } while (currentDividendDigitRearIndex < dividendLength - 1);
        boolean isResultNegativeInteger = (BigInteger.ZERO.compareTo(dividend) > 0) ^ (BigInteger.ZERO.compareTo(divisor) > 0);
        if (isResultNegativeInteger) {
            stepResults.get(0).quotient = BigInteger.valueOf(-1).multiply(stepResults.get(0).quotient);
        }
        if (BigInteger.ZERO.compareTo(dividend) > 0) {
            BigInteger.valueOf(-1).multiply(stepResults.get(stepResults.size() - 1).remainder);
        }
        return stepResults;
    }

    private BigInteger calculateBigQuotient(List<IntermediateBigResult> stepResults) {
        if (null == stepResults) {
            throw new IllegalArgumentException(EMPTY_RESULTS_MESSAGE);
        }
        BigInteger quotient = BigInteger.ZERO;
        for (IntermediateBigResult step : stepResults) {
            quotient = BigInteger.TEN.multiply(quotient).add(step.quotient.abs());
        }
        if (BigInteger.ZERO.compareTo(stepResults.get(0).quotient) > 0) {
            quotient = BigInteger.valueOf(-1).multiply(quotient);
        }
        return quotient;
    }

    private String getRepeatingSymbols( String symbols, int repeatAmount) {
        StringBuilder repeatingSymbols = new StringBuilder();
        for (int i = 0; i < repeatAmount; i++) {
            repeatingSymbols.append(symbols);
        }
        return  repeatingSymbols.toString();
    }

    private String getOffsetSpaces(int offset) {
        return getRepeatingSymbols(" ", offset);
    }

    private String getLine(int length) {
        return getRepeatingSymbols("-", length);
    }

    public String printLongDivision(BigInteger dividend, BigInteger divisor) {
        StringBuilder resultToPrint = new StringBuilder();
        if ((dividend == null) || (divisor == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        List<IntermediateBigResult> stepResults = performLongDivision(dividend, divisor);
        BigInteger absoluteDividend = dividend.abs();
        int absoluteDividendLength = absoluteDividend.toString().length();
        System.out.println();
        resultToPrint.append("_" + dividend + " | " + divisor + "\n");
        int stepCounter = 0;
        for (IntermediateBigResult step : stepResults) {
            int minuendLength = String.valueOf(step.minuend).length();
            int subtrahendLength = String.valueOf(step.subtrahend).length();
            int remainderLength = String.valueOf(step.remainder).length();
            int offsetMinuend= step.rearIndex - minuendLength + 1;
            int offsetSubtrahend = step.rearIndex - subtrahendLength + 1;
            int offsetRemainder = offsetMinuend +  minuendLength - remainderLength + 1;
            String additionalOffsetForVertical = "";
            if (BigInteger.ZERO.compareTo(dividend) > 0) {
                offsetMinuend++;
                offsetSubtrahend++;
                offsetRemainder++;
                additionalOffsetForVertical = " ";
            }
            if (stepCounter > 0) {
                if (!BigInteger.ZERO.equals(step.subtrahend)) {
                    resultToPrint.append(getOffsetSpaces(offsetMinuend) + "_" + step.minuend +"\n");
                }
            }
            if ((!BigInteger.ZERO.equals(step.subtrahend)) || (stepResults.size() == 1)) {
                if (stepCounter == 0) {
                    BigInteger quotient = calculateBigQuotient(stepResults);
                    int quotientLength = String.valueOf(quotient).length();
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + step.subtrahend +
                            getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1));
                    resultToPrint.append(additionalOffsetForVertical + "|" + getLine(quotientLength + 2) + "\n");
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + getLine(subtrahendLength) +
                            getOffsetSpaces(absoluteDividendLength - subtrahendLength - offsetSubtrahend + 1) +
                            additionalOffsetForVertical + "| " + quotient + "\n");
                } else {
                    resultToPrint.append(getOffsetSpaces(offsetSubtrahend + 1) + step.subtrahend);
                    resultToPrint.append("\n" + getOffsetSpaces(offsetSubtrahend + 1) + getLine(subtrahendLength) +"\n");
                }
            }
            stepCounter++;
            if (stepCounter == stepResults.size()) {
                resultToPrint.append(getOffsetSpaces(offsetRemainder) + step.remainder);
            }
        }
        return resultToPrint.toString();
    }
}
