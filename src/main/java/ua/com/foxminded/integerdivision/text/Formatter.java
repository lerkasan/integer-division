package ua.com.foxminded.integerdivision.text;

// TODO lookin on this class I would say it's a way too small, and there's nothing surprising about that coz all the hardcore formatting is in the operations
public class Formatter {

    public String getRepeatingSymbols(String symbols, int repeatAmount) {
        StringBuilder repeatingSymbols = new StringBuilder();
        for (int i = 0; i < repeatAmount; i++) {
            repeatingSymbols.append(symbols);
        }
        return  repeatingSymbols.toString();
    }

    public String getOffsetSpaces(int offset) {
        return getRepeatingSymbols(" ", offset);
    }

    public String getLine(int length) {
        return getRepeatingSymbols("-", length);
    }

    public String deleteLeadingZeros(String number) {
        while (number.startsWith("0") && number.length() > 1) {
            number = number.substring(1);
        }
        return number;
    }
}
