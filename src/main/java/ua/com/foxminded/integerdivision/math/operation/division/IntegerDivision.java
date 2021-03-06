package ua.com.foxminded.integerdivision.math.operation.division;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IntegerDivision extends Operation {
    public static final String DIVISION_BY_ZERO_MESSAGE = "Can't divide by zero.";

    private BigInteger dividend;
    private BigInteger divisor;
    private DivisionResult result;

    public IntegerDivision(BigInteger dividend, BigInteger divisor) {
        super(2,0, Arrays.asList(dividend, divisor));
        this.dividend = dividend;
        this.divisor = divisor;
        this.result = calculate();
    }

    public DivisionResult getResult() {
        return result;
    }

    public BigInteger getNumericResult() {
        return result.getQuotient();
    }

    public BigInteger getDividend() {
        return dividend;
    }

    public void setDividend(BigInteger dividend) {
        this.dividend = dividend;
    }

    public BigInteger getDivisor() {
        return divisor;
    }

    public void setDivisor(BigInteger divisor) {
        this.divisor = divisor;
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
            return new DivisionResult(BigInteger.ZERO, BigInteger.ZERO, Collections.singletonList(new IntermediateDivisionResult()));
        }
        BigInteger absoluteDividend = dividend.abs();
        BigInteger absoluteDivisor = divisor.abs();
        int dividendLength = absoluteDividend.toString().length();
        if (absoluteDividend.compareTo(absoluteDivisor) < 0) {
            List<IntermediateDivisionResult> steps =
                    Collections.singletonList(new IntermediateDivisionResult(dividend, BigInteger.ZERO, dividend, BigInteger.ZERO, dividendLength - 1));
            return new DivisionResult(BigInteger.ZERO, dividend, steps);
        }
        if (dividend.compareTo(divisor) == 0) {
            List<IntermediateDivisionResult> steps =
                    Collections.singletonList(new IntermediateDivisionResult(dividend, divisor, BigInteger.ZERO, BigInteger.ONE, dividendLength - 1));
            return new DivisionResult(BigInteger.ONE, BigInteger.ZERO, steps);
        }
        BigInteger previousRemainder = BigInteger.ZERO;
        int divisorLength = absoluteDivisor.toString().length();
        int currentDividendDigitRearIndex = divisorLength - 1;
        List<IntermediateDivisionResult> stepResults = new ArrayList<>();
        DivisionResult result = new DivisionResult();
        do {
            IntermediateDivisionResult stepResult = new IntermediateDivisionResult();
            if (currentDividendDigitRearIndex == divisorLength - 1) { // first step
                stepResult.setMinuend(findFirstDigits(absoluteDividend, currentDividendDigitRearIndex));

                if (absoluteDivisor.compareTo(stepResult.getMinuend()) > 0) {
                    currentDividendDigitRearIndex++;
                    int nextDigitInDividend = findDigitAtIndex(absoluteDividend, currentDividendDigitRearIndex);
                    stepResult.setMinuend(BigInteger.TEN.multiply(stepResult.getMinuend()).add(BigInteger.valueOf(nextDigitInDividend)));
                }
                stepResult.setDifference(stepResult.getMinuend());
            } else {
                stepResult.setDifference(previousRemainder);
                int nextDigitInDividend = findDigitAtIndex(absoluteDividend, currentDividendDigitRearIndex);
                if (BigInteger.ZERO.equals(stepResult.getDifference())) {
                    stepResult.setMinuend(BigInteger.valueOf(nextDigitInDividend));
                } else {
                    stepResult.setMinuend(BigInteger.TEN.multiply(stepResult.getDifference()).add(BigInteger.valueOf(nextDigitInDividend)));
                }
            }
            stepResult.setQuotient(stepResult.getMinuend().divide(absoluteDivisor));
            stepResult.setSubtrahend(stepResult.getQuotient().multiply(absoluteDivisor));
            stepResult.setDifference(stepResult.getMinuend().subtract(stepResult.getSubtrahend()));
            stepResult.setRearIndex(currentDividendDigitRearIndex);
            previousRemainder = stepResult.getDifference();
            stepResults.add(stepResult);
            currentDividendDigitRearIndex++;
        } while (currentDividendDigitRearIndex <= dividendLength - 1);
        boolean isResultNegativeInteger = (BigInteger.ZERO.compareTo(dividend) > 0) ^ (BigInteger.ZERO.compareTo(divisor) > 0);
        if (isResultNegativeInteger) {
            stepResults.get(0).setQuotient(BigInteger.valueOf(-1).multiply(stepResults.get(0).getQuotient()));
        }
        if (BigInteger.ZERO.compareTo(dividend) > 0) {
           result.setRemainder(BigInteger.valueOf(-1).multiply(stepResults.get(stepResults.size() - 1).getDifference()));
           stepResults.get(stepResults.size() - 1).setDifference(result.getRemainder());
        } else {
            result.setRemainder(stepResults.get(stepResults.size() - 1).getDifference());
        }
        BigInteger wholeQuotient = calculateWholeQuotient(stepResults);
        return new DivisionResult(wholeQuotient, result.getRemainder(), stepResults);
    }

    private BigInteger calculateWholeQuotient(List<IntermediateDivisionResult> stepResults) {
        if (null == stepResults) {
            throw new IllegalArgumentException(EMPTY_RESULTS_MESSAGE);
        }
        BigInteger quotient = BigInteger.ZERO;
        for (IntermediateDivisionResult step : stepResults) {
            quotient = BigInteger.TEN.multiply(quotient).add(step.getQuotient().abs());
        }
        if (BigInteger.ZERO.compareTo(stepResults.get(0).getQuotient()) > 0) {
            quotient = BigInteger.valueOf(-1).multiply(quotient);
        }
        return quotient;
    }

    @Override
    public String toString() {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        Formatter formatter = formatterFactory.getDivisionFormatter();
        return formatter.formatOutput(this);
    }
}
