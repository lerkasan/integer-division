package ua.com.foxminded.integerdivision.text.formatter;

public class SubtractionFormatter extends AdditionFormatter {

    private static final char operationSign = '-';

    public SubtractionFormatter(String outputType) {
        super(outputType, operationSign);
    }
}
