package ua.com.foxminded.integerdivision.text;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.integerdivision.math.Operation;
import ua.com.foxminded.integerdivision.text.Formatter;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatterTest {

    private Formatter underTest = new Formatter();

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

    @Test
    void shouldDeleteLeadingZeros() {
        String inputWithLeadingZeros = "00000726035000325002320";
        String expected = "726035000325002320";
        String actual = underTest.deleteLeadingZeros(inputWithLeadingZeros);
        assertEquals(expected, actual);
    }
}
