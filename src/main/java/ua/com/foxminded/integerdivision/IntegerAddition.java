package ua.com.foxminded.integerdivision;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerAddition extends Operation {
    private BigInteger firstAddend;
    private BigInteger secondAddend;
    private AdditionResult result;

    public IntegerAddition(BigInteger firstAddend, BigInteger secondAddend) {
        super(2, 1, Arrays.asList(firstAddend, secondAddend));
        this.firstAddend = firstAddend;
        this.secondAddend = secondAddend;
    }

    protected class AdditionResult extends Result {
        @JsonProperty
        private String sum;

        @JsonProperty
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
        @JsonProperty
        private int digit;

        @JsonProperty
        private int memorized;
    }

    public String getResult() {
        return result.sum;
    }

    public AdditionResult calculate() {
        result = calculateWithoutAssignment();
        return result;
    }

    private AdditionResult calculateWithoutAssignment() {
        if ((firstAddend == null) || (secondAddend == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
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
             if (!"0".equals(positiveResult.sum)) {
                 String sum = "-" + positiveResult.sum;
                 return new AdditionResult(sum, positiveResult.steps);
             }
             return positiveResult;
        }
        String sum ="";
        if ((BigInteger.ZERO.compareTo(firstAddend) > 0) && (BigInteger.ZERO.compareTo(secondAddend) < 0)) {
            return subtractDigits(secondAddend,  BigInteger.valueOf(-1).multiply(firstAddend));
//            AdditionResult invertedResult = subtractDigits(firstAddend, secondAddend);
//            char firstCharOfInvertedSum = invertedResult.sum.charAt(0);
//            if (firstCharOfInvertedSum == '-') {
//                sum = invertedResult.sum.substring(1);
//            } else {
//                if ("0".equals(invertedResult.sum)) {
//                    return invertedResult;
//                }
//                sum = "-" + invertedResult.sum;
//            }
//            return new AdditionResult(sum, invertedResult.steps);
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) < 0) && (BigInteger.ZERO.compareTo(secondAddend) > 0)) {
            return subtractDigits(firstAddend, BigInteger.valueOf(-1).multiply(secondAddend));
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
                int firstDigit = findDigitAtIndex(absoluteFirstAddend, index);
                int secondDigit = 0;
                if (index >= lengthDelta) {
                    secondDigit = findDigitAtIndex(absoluteSecondAddend, index - lengthDelta);
                }

//                step.digit = (firstDigit + secondDigit + step.memorized) % 10;
                step.digit = (firstDigit + secondDigit + previousMemorized) % 10;
                step.memorized = (firstDigit + secondDigit + previousMemorized) / 10;
                previousMemorized = step.memorized;
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            IntermediateAdditionResult lastStep = steps.get(steps.size() - 1);
            if (lastStep.memorized != 0) {
                sum.append(lastStep.memorized);
            }
            String finalSum = combineSumDigits((List<IntermediateAdditionResult>) steps, sum);
            return new AdditionResult(finalSum, steps);
        } else {
            return addDigits(secondAddend, firstAddend);
        }
    }

    private AdditionResult subtractDigits(BigInteger firstAddend, BigInteger secondAddend) {
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
                int firstDigit = findDigitAtIndex(absoluteFirstAddend, index);
                int secondDigit = 0;
                if (index >= lengthDelta) {
                    secondDigit = findDigitAtIndex(absoluteSecondAddend, index - lengthDelta);
                }

                if ((firstDigit + previousMemorized < secondDigit) && (firstAddendLength >= secondAddendLength) && (absoluteFirstAddend.compareTo(absoluteSecondAddend) > 0)) {
                    step.memorized = -1;
                } else {
                    step.memorized = 0;
                }
                step.digit = Math.abs((firstDigit - secondDigit + previousMemorized - step.memorized * 10) % 10);
//                step.digit = (firstDigit - secondDigit + previousMemorized - step.memorized * 10) % 10;
                previousMemorized = step.memorized;
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            IntermediateAdditionResult lastStep = steps.get(steps.size() - 1);
            String finalSum = combineSumDigits(steps, sum);
            Formatter formatter = new Formatter();
            finalSum = formatter.deleteLeadingZeros(finalSum);
            if ((firstAddend.compareTo(secondAddend) < 0) && (!finalSum.startsWith("-"))) {
                finalSum = "-" + finalSum;
            }
            return new AdditionResult(finalSum, steps);
        } else {
            AdditionResult negativeResult = subtractDigits(BigInteger.valueOf(-1).multiply(secondAddend), BigInteger.valueOf(-1).multiply(firstAddend));
            String finalSum = negativeResult.sum;
            if (!"0".equals(negativeResult.sum) && !finalSum.startsWith("-")) {
                finalSum = "-" + negativeResult.sum;
//                return new AdditionResult(finalSum, negativeResult.steps);
            }
//            return negativeResult;
            return new AdditionResult(finalSum, negativeResult.steps);
        }
    }

    private String combineSumDigits(List<IntermediateAdditionResult> steps, StringBuilder sum) {
        for (int index = steps.size() - 1; index >= 0; index--) {
            sum.append(steps.get(index).digit);
        }
        String finalSum = sum.toString();
        if (BigInteger.ZERO.equals(new BigInteger(finalSum))) {
            finalSum = "0";
        }
        return finalSum;
    }

    public String toString() {
        String result = formatOutput('+');
        System.out.println();
        return result;
    }

    protected String formatOutput(char operationSign) {
        Formatter formatter = new Formatter();
        String result = "";
        String sum = calculate().sum;
        if (operationSign == '-') {
            secondAddend = BigInteger.valueOf(-1).multiply(secondAddend);
        }
        int firstAddendLength = firstAddend.toString().length();
        int secondAddendLength = secondAddend.toString().length();
        int firstAddendOffset = sum.length() - firstAddendLength;
        int secondAddendOffset = sum.length() - secondAddendLength;
        int maxLengthOfAddend = firstAddendLength >= secondAddendLength ? firstAddendLength : secondAddendLength;
        if (sum.length() >= maxLengthOfAddend) {
            result = formatter.getOffsetSpaces(firstAddendOffset + 2) + firstAddend + "\n"
                    + formatter.getOffsetSpaces(secondAddendOffset + 2) + secondAddend + "\n"
                    + operationSign + " " + formatter.getLine(sum.length()) + "\n"
                    + formatter.getOffsetSpaces(2) + sum;
        } else {
            firstAddendOffset = maxLengthOfAddend - firstAddendLength;
            secondAddendOffset = maxLengthOfAddend - secondAddendLength;
            result = formatter.getOffsetSpaces(firstAddendOffset + 2) + firstAddend + "\n"
                    + formatter.getOffsetSpaces(secondAddendOffset + 2) + secondAddend + "\n"
                    + operationSign + " " + formatter.getLine(maxLengthOfAddend) + "\n"
                    + formatter.getOffsetSpaces(maxLengthOfAddend - sum.length() + 2) + sum;
        }
        return result;
    }
}
