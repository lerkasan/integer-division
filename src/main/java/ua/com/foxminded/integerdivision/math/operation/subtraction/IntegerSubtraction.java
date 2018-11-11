package ua.com.foxminded.integerdivision.math.operation.subtraction;

import ua.com.foxminded.integerdivision.math.operation.addition.IntegerAddition;
import ua.com.foxminded.integerdivision.text.factory.FormatterFactory;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import java.math.BigInteger;

public class IntegerSubtraction extends IntegerAddition {

    public IntegerSubtraction(BigInteger minuend, BigInteger subtrahend) {
        super(minuend, BigInteger.valueOf(-1).multiply(subtrahend));
    }

    @Override
    public String toString() {
        FormatterFactory formatterFactory = FormatterFactory.getClassicFormatterFactory();
        Formatter formatter = formatterFactory.getSubtractionFormatter();
        return formatter.formatOutput(this);
    }
}
