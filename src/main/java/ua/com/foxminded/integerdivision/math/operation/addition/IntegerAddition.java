package ua.com.foxminded.integerdivision.math.operation.addition;

import ua.com.foxminded.integerdivision.math.operation.Operation;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

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
        this.result = calculate();
    }

    public AdditionResult getResult() {
        return result;
    }

    public BigInteger getNumericResult() {
        return result.getSum();
    }

    public BigInteger getFirstAddend() {
        return firstAddend;
    }

    public void setFirstAddend(BigInteger firstAddend) {
        this.firstAddend = firstAddend;
    }

    public BigInteger getSecondAddend() {
        return secondAddend;
    }

    public void setSecondAddend(BigInteger secondAddend) {
        this.secondAddend = secondAddend;
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
            finalSum = deleteLeadingZeros(finalSum);
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

    @Override
    public String toString() {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        Formatter formatter = formatterFactory.getAdditionFormatter();
        return formatter.formatOutput(this);
    }
}
