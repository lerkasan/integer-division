package ua.com.foxminded.integerdivision.text.factory;

import ua.com.foxminded.integerdivision.text.formatter.*;

public class ClassicFormatterFactory extends FormatterFactory {

    public Formatter getAdditionFormatter() {
        return new AdditionFormatter("classic");
    }

    public Formatter getSubtractionFormatter() {
        return new SubtractionFormatter("classic");
    }

    public Formatter getMultiplicationFormatter() {
        return new MultiplicationFormatter("classic");
    }

    public Formatter getDivisionFormatter() {
        return new DivisionFormatter("classic");
    }
}
