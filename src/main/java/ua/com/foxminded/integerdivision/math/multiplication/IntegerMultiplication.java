package ua.com.foxminded.integerdivision.math.multiplication;

import ua.com.foxminded.integerdivision.math.Operation;
import ua.com.foxminded.integerdivision.math.addition.IntegerAddition;
import ua.com.foxminded.integerdivision.text.Formatter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerMultiplication extends Operation {
    private BigInteger multiplicand;
    private BigInteger multiplier;
    private MultiplicationResult result;

    private Formatter formatter = new Formatter();

    public IntegerMultiplication(BigInteger multiplicand, BigInteger multiplier) {
        super(2, 1, Arrays.asList(multiplicand, multiplier));
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.result = calculate();
    }

    public BigInteger getResult() {
        return result.getProduct();
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
            String zerosAtAddendTail = formatter.getRepeatingSymbols("0", index);
            IntegerAddition addition = new IntegerAddition(sum, new BigInteger(steps.get(index).getAddend() + zerosAtAddendTail));
            sum = addition.getResult();
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

    public String toString() {
        int multiplicandLength = multiplicand.toString().length();
        int multiplierLength = multiplier.toString().length();
        int productLength = result.getProduct().toString().length();
        int operandMaxLength = multiplierLength > multiplicandLength ? multiplierLength : multiplicandLength;
        int maxLength = operandMaxLength > productLength ? operandMaxLength : productLength;
        int multiplicandOffset = maxLength - multiplicandLength;
        int multiplierOffset = maxLength - multiplierLength;
        int productOffset = maxLength - productLength;
        StringBuilder output = new StringBuilder();
        output.append(formatter.getOffsetSpaces(multiplicandOffset + 2) + multiplicand.toString() + "\n"
                + formatter.getOffsetSpaces(multiplierOffset + 2) + multiplier.toString() + "\n"
                + formatter.getOffsetSpaces(maxLength - operandMaxLength) + "* " + formatter.getLine(operandMaxLength) + "\n");
        if (!BigInteger.ZERO.equals(result.getProduct()) && (result.getSteps().size() > 1)) {
            for (IntermediateMultiplicationResult step : result.getSteps()) {
                int addendOffset = maxLength - step.getAddend().length();
                if (!BigInteger.ZERO.equals(new BigInteger(step.getAddend()))) {
                    output.append(formatter.getOffsetSpaces(addendOffset - step.getRearIndex() + 2) + step.getAddend() + "\n");
                }
            }
            output.append("+ " + formatter.getOffsetSpaces(productOffset) + formatter.getLine(maxLength) + "\n");
        }
        output.append(formatter.getOffsetSpaces(productOffset + 2) + result.getProduct());
        return output.toString();
    }
}
