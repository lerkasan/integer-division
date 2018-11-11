package ua.com.foxminded.integerdivision.math.operation.multiplication;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.math.operation.addition.IntegerAddition;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerMultiplication extends Operation {
    private BigInteger multiplicand;
    private BigInteger multiplier;
    private MultiplicationResult result;

    public IntegerMultiplication(BigInteger multiplicand, BigInteger multiplier) {
        super(2, 1, Arrays.asList(multiplicand, multiplier));
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.result = calculate();
    }

    public MultiplicationResult getResult() {
        return result;
    }

    public BigInteger getNumericResult() {
        return result.getProduct();
    }

    public BigInteger getMultiplicand() {
        return multiplicand;
    }

    public void setMultiplicand(BigInteger multiplicand) {
        this.multiplicand = multiplicand;
    }

    public BigInteger getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigInteger multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    protected MultiplicationResult calculate() {
        if ((multiplicand == null) || (multiplier == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        if (BigInteger.ZERO.equals(multiplicand)) {
            return new MultiplicationResult(BigInteger.ZERO);

        }
        if (BigInteger.ZERO.equals(multiplier)) {
            return new MultiplicationResult(BigInteger.ZERO);
        }
        BigInteger absoluteMultiplicand = multiplicand.abs();
        BigInteger absoluteMultiplier= multiplier.abs();
        int multiplicandLength = absoluteMultiplicand.toString().length();
        int multiplierLength = absoluteMultiplier.toString().length();
        List<IntermediateMultiplicationResult> steps = new ArrayList<>();
        for (int multiplierIndex = multiplierLength - 1; multiplierIndex >= 0; multiplierIndex--) {
            int multiplierDigit = findDigitAtIndex(absoluteMultiplier, multiplierIndex);
            int memorized = 0;
            List<Integer> digits = new ArrayList<>();
            IntermediateMultiplicationResult step = new IntermediateMultiplicationResult();
            for (int multiplicandIndex = multiplicandLength - 1; multiplicandIndex >= 0; multiplicandIndex--) {
                int multiplicandDigit = findDigitAtIndex(absoluteMultiplicand, multiplicandIndex);
                int digit = (multiplicandDigit * multiplierDigit + memorized) % 10;
                memorized = (multiplicandDigit * multiplierDigit + memorized) / 10;
                digits.add(digit);
            }
            if (memorized != 0) {
                digits.add(memorized);
            }
            step.setAddend(combineAddendDigits(digits));
            step.setRearIndex(multiplierLength - multiplierIndex - 1);
            steps.add(step);
        }

        boolean isResultNegativeInteger = (BigInteger.ZERO.compareTo(multiplicand) > 0) ^ (BigInteger.ZERO.compareTo(multiplier) > 0);
        BigInteger product = addAddends(steps);
        if (isResultNegativeInteger) {
             product = BigInteger.valueOf(-1).multiply(product);
        }
        return new MultiplicationResult(product, steps);
    }

    private BigInteger addAddends(List<IntermediateMultiplicationResult> steps) {
        BigInteger sum = BigInteger.ZERO;
        for (int index = 0; index < steps.size(); index++) {
            String zerosAtAddendTail = Formatter.getRepeatingSymbols("0", index);
            IntegerAddition addition = new IntegerAddition(sum, new BigInteger(steps.get(index).getAddend() + zerosAtAddendTail));
            sum = addition.getNumericResult();
        }
        return sum;
    }

    private String combineAddendDigits(List<Integer> digits) {
        StringBuilder addend = new StringBuilder();
        for (int index = digits.size() - 1; index >= 0; index--) {
            addend.append(digits.get(index));
        }
        return addend.toString();
    }

    @Override
    public String toString() {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        Formatter formatter = formatterFactory.getMultiplicationFormatter();
        return formatter.formatOutput(this);
    }
}

