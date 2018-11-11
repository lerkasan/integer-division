package ua.com.foxminded.integerdivision.text.factory;

import ua.com.foxminded.integerdivision.text.formatter.Formatter;

public abstract class FormatterFactory {

    private static final String NULL_TYPE_MESSAGE = "FormatterFactory type parameter is null.";
    private static final String ILLEGAL_TYPE_MESSAGE = "Illegal format parameter ";

    public static FormatterFactory getFormatterFactory(String type) {
        if (type == null) {
            throw new IllegalArgumentException(NULL_TYPE_MESSAGE);
        }
        type = type.toLowerCase();
        switch (type) {
            case "json":
                return new JsonFormatterFactory();
            case "classic":
                return new ClassicFormatterFactory();
            default:
                throw new IllegalArgumentException(ILLEGAL_TYPE_MESSAGE + type);
        }
    }

    public static ClassicFormatterFactory getClassicFormatterFactory() {
        return new ClassicFormatterFactory();
    }

    public static JsonFormatterFactory getJsonFormatterFactory() {
        return new JsonFormatterFactory();
    }

    public abstract Formatter getAdditionFormatter();

    public abstract Formatter getSubtractionFormatter();

    public abstract Formatter getMultiplicationFormatter();

    public abstract Formatter getDivisionFormatter();
}
