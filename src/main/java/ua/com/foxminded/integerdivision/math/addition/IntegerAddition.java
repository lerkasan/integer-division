package ua.com.foxminded.integerdivision.math.addition;

import ua.com.foxminded.integerdivision.math.Operation;
import ua.com.foxminded.integerdivision.text.Formatter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerAddition extends Operation {
    private BigInteger firstAddend;
    private BigInteger secondAddend;
    private AdditionResult result;

    private Formatter formatter = new Formatter();

    public IntegerAddition(BigInteger firstAddend, BigInteger secondAddend) {
        super(2, 1, Arrays.asList(firstAddend, secondAddend));
        this.firstAddend = firstAddend;
        this.secondAddend = secondAddend;
        this.result = calculate();
    }
    
    public BigInteger getResult() {
        return result.getSum();
    }

    @Override
    public AdditionResult calculate() {
        if ((firstAddend == null) || (secondAddend == null)) {
            throw new IllegalArgumentException(NULL_ARGUMENT_MESSAGE);
        }
        AdditionResult result = new AdditionResult();
        if (BigInteger.ZERO.equals(firstAddend)) {
            return new AdditionResult(secondAddend);
        }
        if (BigInteger.ZERO.equals(secondAddend)) {
            return new AdditionResult(firstAddend);
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) < 0) && (BigInteger.ZERO.compareTo(secondAddend) < 0)) {
            return addDigits(firstAddend, secondAddend);
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) > 0) && (BigInteger.ZERO.compareTo(secondAddend) > 0)) {
             AdditionResult positiveResult = addDigits(firstAddend, secondAddend);
             if (!BigInteger.ZERO.equals(positiveResult.getSum())) {
                 String sum = "-" + positiveResult.getSum();
                 return new AdditionResult(new BigInteger(sum), positiveResult.getSteps());
             }
             return positiveResult;
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) > 0) && (BigInteger.ZERO.compareTo(secondAddend) < 0)) {
            return subtractDigits(secondAddend,  BigInteger.valueOf(-1).multiply(firstAddend));
        }
        if ((BigInteger.ZERO.compareTo(firstAddend) < 0) && (BigInteger.ZERO.compareTo(secondAddend) > 0)) {
            return subtractDigits(firstAddend, BigInteger.valueOf(-1).multiply(secondAddend));
        }
        return result;
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
                step.setDigit((firstDigit + secondDigit + previousMemorized) % 10);
                step.setMemorized((firstDigit + secondDigit + previousMemorized) / 10);
                previousMemorized = step.getMemorized();
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            IntermediateAdditionResult lastStep = steps.get(steps.size() - 1);
            if (lastStep.getMemorized() != 0) {
                sum.append(lastStep.getMemorized());
            }
            String finalSum = combineSumDigits(steps, sum);
            return new AdditionResult(new BigInteger(finalSum), steps);
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

                if ((firstDigit + previousMemorized < secondDigit) && (absoluteFirstAddend.compareTo(absoluteSecondAddend) > 0)) {
                    step.setMemorized(-1);
                } else {
                    step.setMemorized(0);
                }
                step.setDigit(Math.abs((firstDigit - secondDigit + previousMemorized - step.getMemorized() * 10) % 10));
                previousMemorized = step.getMemorized();
                steps.add(step);
            }
            StringBuilder sum = new StringBuilder();
            String finalSum = combineSumDigits(steps, sum);
            finalSum = formatter.deleteLeadingZeros(finalSum);
            if ((firstAddend.compareTo(secondAddend) < 0) && (!finalSum.startsWith("-"))) {
                finalSum = "-" + finalSum;
            }
            return new AdditionResult(new BigInteger(finalSum), steps);
        } else {
            AdditionResult negativeResult = subtractDigits(BigInteger.valueOf(-1).multiply(secondAddend), BigInteger.valueOf(-1).multiply(firstAddend));
            BigInteger finalSum = negativeResult.getSum();
            if (!BigInteger.ZERO.equals(negativeResult.getSum()) && (BigInteger.ZERO.compareTo(finalSum) <= 0)) {
                finalSum = BigInteger.valueOf(-1).multiply(negativeResult.getSum());
            }
            return new AdditionResult(finalSum, negativeResult.getSteps());
        }
    }

    private String combineSumDigits(List<IntermediateAdditionResult> steps, StringBuilder sum) {
        for (int index = steps.size() - 1; index >= 0; index--) {
            sum.append(steps.get(index).getDigit());
        }
        String finalSum = sum.toString();
        if (BigInteger.ZERO.equals(new BigInteger(finalSum))) {
            finalSum = "0";
        }
        return finalSum;
    }

    public String toString() {
        return formatOutput('+');
    }

    // TODO Speaking honestly we do the Formatter's job right here so we mixed 'math' and 'test' concers. Which is SRP & high cohesion principles violation. :(
    // Can we think out how to follow that principles? Maybe you could try to xomehow decouple, extract this into separate, maybe special formatter of addition? Then we could look what GOF pattern can be employed (Strategy?, Bridge?)
    protected String formatOutput(char operationSign) {
        String result;
        String sum = getResult().toString();
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
