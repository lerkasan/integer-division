package ua.com.foxminded.integerdivision.text.factory;

import ua.com.foxminded.integerdivision.text.formatter.*;

public class JsonFormatterFactory extends FormatterFactory {

    public Formatter getAdditionFormatter() {
        return new AdditionFormatter("json");
    }

    public Formatter getSubtractionFormatter() {
        return new SubtractionFormatter("json");
    }

    public Formatter getMultiplicationFormatter() {
        return new MultiplicationFormatter("json");
    }

    public Formatter getDivisionFormatter() {
        return new DivisionFormatter("json");
    }

}
