package ua.com.foxminded.integerdivision;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerAddition extends Operation {
    private BigInteger firstAddend;
    private BigInteger secondAddend;

    public IntegerAddition(BigInteger firstAddend, BigInteger secondAddend) {
        super(2, 1, Arrays.asList(firstAddend, secondAddend));
        this.firstAddend = firstAddend;
        this.secondAddend = secondAddend;
    }

    private class AdditionResult extends Result {
        private String sum;
        private List<IntermediateAdditionResult> steps;

        private AdditionResult() {
        }

        private AdditionResult(String sum) {
            this.sum = sum;
        }

        private AdditionResult(String sum, List<IntermediateAdditionResult> steps) {
            this.sum = sum;
            this.steps = steps;
        }
    }

    private class IntermediateAdditionResult extends Result {
        private int digit;
        private int memorized;
    }

    public AdditionResult calculate() {
        if ((firstAddend == null) || (secondAddend == null)) {
            throw new IllegalArgumentException(this.NULL_ARGUMENT_MESSAGE);
        }
        AdditionResult result = new AdditionResult();
        if (BigInteger.ZERO.equals(firstAddend)) {
            return new AdditionResult(secondAddend.toString());

        } if (BigInteger.ZERO.equals(secondAddend)) {
            return new AdditionResult(firstAddend.toString());
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) < 0) && (BigInteger.ZERO.compareTo(secondAddend) < 0)) {
            return addDigits(firstAddend, secondAddend);
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) > 0) && (BigInteger.ZERO.compareTo(secondAddend) > 0)) {
             AdditionResult positiveResult = addDigits(firstAddend, secondAddend);
             String sum = "-" + positiveResult.sum;
             return new AdditionResult(sum, positiveResult.steps);
        }
        String sum ="";
        if ((BigInteger.ZERO.compareTo(firstAddend) > 0) && (BigInteger.ZERO.compareTo(secondAddend) < 0)) {
            AdditionResult invertedResult = substractDigits(firstAddend, secondAddend);
            char firstCharOfSum = invertedResult.sum.charAt(0);
            if (firstCharOfSum == '-') {
                sum = invertedResult.sum.substring(1);
            } else {
                sum = "-" + invertedResult.sum;
            }
            return new AdditionResult(sum, invertedResult.steps);
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) < 0) && (BigInteger.ZERO.compareTo(secondAddend) > 0)) {
            return substractDigits(firstAddend, secondAddend);
        }
        return  result;
    }

    private AdditionResult addDigits(BigInteger firstAddend, BigInteger secondAddend) {
        BigInteger absoluteFirstAddend = firstAddend.abs();
        BigInteger absoluteSecondAddend = secondAddend.abs();
        int firstAddendLength = absoluteFirstAddend.toString().length();
        int secondAddendLength = absoluteSecondAddend.toString().length();
        int maxLength = 0;
        int lengthDelta = 0;
        int previousMemorized = 0;
        lengthDelta = Math.abs(firstAddendLength - secondAddendLength);
        if (firstAddendLength >= secondAddendLength) {
            maxLength = firstAddendLength;
            List<IntermediateAdditionResult> steps = new ArrayList<>();
            for (int index = maxLength - 1; index >= 0; index--) {
                IntermediateAdditionResult step = new IntermediateAdditionResult();
                int firstDigit = findDigitAtIndex(firstAddend.abs(), index);
                int secondDigit = 0;
                if (index >= lengthDelta) {
                    secondDigit = findDigitAtIndex(secondAddend.abs(), index - lengthDelta);
                }
                step.digit = (firstDigit + secondDigit + previousMemorized) % 10;
                step.memorized = (firstDigit + secondDigit) / 10;
                previousMemorized = step.memorized;
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            IntermediateAdditionResult lastStep = steps.get(steps.size() - 1);
            if (lastStep.memorized != 0) {
                sum.append(lastStep.memorized);
            }
            for (int index = steps.size() - 1; index >= 0; index--) {
                sum.append(steps.get(index).digit);
            }
            return new AdditionResult(sum.toString(), steps);
        } else {
            return addDigits(secondAddend, firstAddend);
        }
    }

    private AdditionResult substractDigits(BigInteger firstAddend, BigInteger secondAddend) {
        BigInteger absoluteFirstAddend = firstAddend.abs();
        BigInteger absoluteSecondAddend = secondAddend.abs();
        int firstAddendLength = absoluteFirstAddend.toString().length();
        int secondAddendLength = absoluteSecondAddend.toString().length();
        int maxLength = 0;
        int lengthDelta = 0;
        int previousMemorized = 0;
        lengthDelta = Math.abs(firstAddendLength - secondAddendLength);
        if (firstAddendLength >= secondAddendLength) {
            maxLength = firstAddendLength;
            List<IntermediateAdditionResult> steps = new ArrayList<>();
            for (int index = maxLength - 1; index >= 0; index--) {
                IntermediateAdditionResult step = new IntermediateAdditionResult();
                int firstDigit = findDigitAtIndex(firstAddend.abs(), index);
                int secondDigit = 0;
                if (index >= lengthDelta) {
                    secondDigit = findDigitAtIndex(secondAddend.abs(), index - lengthDelta);
                }
                if (firstDigit < secondDigit) {
                    step.memorized = -1;
                }
                step.digit = (firstDigit - secondDigit + previousMemorized - step.memorized * 10) % 10;
                previousMemorized = step.memorized;
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            IntermediateAdditionResult lastStep = steps.get(steps.size() - 1);
//            if (lastStep.memorized != 0) {
//                sum.append(lastStep.memorized);
//            }
            for (int index = steps.size() - 1; index >= 0; index--) {
                sum.append(steps.get(index).digit);
            }
            return new AdditionResult(sum.toString(), steps);
        } else {
            AdditionResult negativeResult = substractDigits(BigInteger.valueOf(-1).multiply(secondAddend), BigInteger.valueOf(-1).multiply(firstAddend));
            return new AdditionResult("-" + negativeResult.sum, negativeResult.steps);
        }
    }

    public String toString() {
        return calculate().sum;
    }

}
