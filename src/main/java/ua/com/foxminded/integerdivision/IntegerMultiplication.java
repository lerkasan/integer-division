package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerMultiplication extends Operation {
    private BigInteger multiplicand;
    private BigInteger multiplier;

    public IntegerMultiplication(BigInteger multiplicand, BigInteger multiplier) {
        super(2, 1, Arrays.asList(multiplicand, multiplier));
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
    }

    protected class MultiplicationResult extends Result {
        @JsonProperty
        private String product;

        @JsonProperty
        private List<IntermediateMultiplicationResult> steps;

        private MultiplicationResult() {
        }

        private MultiplicationResult(String product) {
            this.product = product;
        }

        private MultiplicationResult(String product, List<IntermediateMultiplicationResult> steps) {
            this.product = product;
            this.steps = steps;
        }
    }

    private class IntermediateMultiplicationResult extends Result {
        @JsonProperty
        private String addend;
    }

    public IntegerMultiplication.MultiplicationResult calculate() {
        if ((multiplicand == null) || (multiplier == null)) {
            throw new IllegalArgumentException(this.NULL_ARGUMENT_MESSAGE);
        }
        if (BigInteger.ZERO.equals(multiplicand)) {
            return new MultiplicationResult("0");

        }
        if (BigInteger.ZERO.equals(multiplier)) {
            return new MultiplicationResult("0");
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
            step.addend = combineAddendDigits(digits);
            step.rearIndex = multiplierLength - multiplierIndex - 1;
//            step.rearIndex = multiplierIndex - multiplierLength - 1;
            steps.add(step);
        }

        boolean isResultNegativeInteger = (BigInteger.ZERO.compareTo(multiplicand) > 0) ^ (BigInteger.ZERO.compareTo(multiplier) > 0);
        String product = addAddends(steps);
        if (isResultNegativeInteger) {
             product = "-" + product;
        }
        MultiplicationResult result = new MultiplicationResult(product, steps);
        return result;
    }

    private String addAddends(List<IntermediateMultiplicationResult> steps) {
        Formatter formatter = new Formatter();
        String sum = "0";
        for (int index = 0; index < steps.size(); index++) {
            String zerosAtAddendTail = formatter.getRepeatingSymbols("0", index);
            IntegerAddition addition = new IntegerAddition(new BigInteger(sum), new BigInteger(steps.get(index).addend + zerosAtAddendTail));
            System.out.println("addend " + steps.get(index).addend + zerosAtAddendTail);
            sum = addition.calculate().getSum();
            System.out.println("step sum " + sum);
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
        Formatter formatter = new Formatter();
        MultiplicationResult result = calculate();
        int multiplicandLength = multiplicand.toString().length();
        int multiplierLength = multiplier.toString().length();
        int productLength = result.product.length();
        int operandMaxLength = multiplierLength > multiplicandLength ? multiplierLength : multiplicandLength;
        int maxLength = operandMaxLength > productLength ? operandMaxLength : productLength;
        int multiplicandOffset = maxLength - multiplicandLength;
        int multiplierOffset = maxLength - multiplierLength;
        int productOffset = maxLength - productLength;

        String output = formatter.getOffsetSpaces(multiplicandOffset + 2) + multiplicand.toString() + "\n"
                + formatter.getOffsetSpaces(multiplierOffset + 2) + multiplier.toString() + "\n"
                + "* " + formatter.getLine(productLength) + "\n";
        for (IntermediateMultiplicationResult step : result.steps) {
            int addendOffset = maxLength - step.addend.length();
            output += formatter.getOffsetSpaces(addendOffset - step.rearIndex + 2) + step.addend + "\n";
        }
        output += "+ " + formatter.getOffsetSpaces(productOffset) + formatter.getLine(maxLength) + "\n"
                + formatter.getOffsetSpaces(productOffset + 2) + result.product;
        System.out.println();
        return output;
    }
}
