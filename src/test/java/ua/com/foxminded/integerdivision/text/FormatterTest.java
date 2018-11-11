package ua.com.foxminded.integerdivision.text;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.integerdivision.text.formatter.AdditionFormatter;
import ua.com.foxminded.integerdivision.text.formatter.Formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTest {

    private Formatter underTest = new AdditionFormatter();

    @Test
    void shouldReturnRepeatingSymbols() {
        String symbols = "abc";
        int repeatAmount = 4;
        String expected = "abcabcabcabc";
        String actual = underTest.getRepeatingSymbols(symbols, repeatAmount);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyRepeatingSymbols() {
        String symbols = "";
        int repeatAmount = 4;
        String expected = "";
        String actual = underTest.getRepeatingSymbols(symbols, repeatAmount);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOffsetSpaces() {
        int offset = 5;
        String expected = "     ";
        String actual = underTest.getOffsetSpaces(offset);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLine() {
        int length = 8;
        String expected = "--------";
        String actual = underTest.getLine(length);
        assertEquals(expected, actual);
    }
}
