package ua.com.foxminded.integerdivision;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerAdditionTest {

    private IntegerAddition underTest;

    @Test
    void shouldPrintLongAdditionWithZeroFirstAddend() {
        BigInteger firstAddend = BigInteger.ZERO;
        BigInteger secondAddend = BigInteger.valueOf(1454360);
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "        0\n" +
                "  1454360\n" +
                "+ -------\n" +
                "  1454360";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithZeroSecondAddend() {
        BigInteger firstAddend = new BigInteger("-4654490235436700265");
        BigInteger secondAddend = BigInteger.ZERO;
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  -4654490235436700265\n" +
                "                     0\n" +
                "+ --------------------\n" +
                "  -4654490235436700265";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithPositiveAddends() {
        BigInteger firstAddend = new BigInteger("142390237823005320");
        BigInteger secondAddend = new BigInteger("909235809253");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  142390237823005320\n" +
                "        909235809253\n" +
                "+ ------------------\n" +
                "  142391147058814573";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithNegativeAddends() {
        BigInteger firstAddend = new BigInteger("-2389237895322306");
        BigInteger secondAddend = new BigInteger("-9823920");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  -2389237895322306\n" +
                "           -9823920\n" +
                "+ -----------------\n" +
                "  -2389237905146226";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithPositiveBiggerAndNegativeAddends() {
        BigInteger firstAddend = new BigInteger("325643612070025");
        BigInteger secondAddend = new BigInteger("-548062382");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  325643612070025\n" +
                "       -548062382\n" +
                "+ ---------------\n" +
                "  325643064007643";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithPositiveAndNegativeBiggerAbsAddends() {
        BigInteger firstAddend = new BigInteger("20749823");
        BigInteger secondAddend = new BigInteger("-970312576003086");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "          20749823\n" +
                "  -970312576003086\n" +
                "+ ----------------\n" +
                "  -970312555253263";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithNegativeBiggerAbsAndPositiveAddends() {
        BigInteger firstAddend = new BigInteger("-902387923");
        BigInteger secondAddend = new BigInteger("1234");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  -902387923\n" +
                "        1234\n" +
                "+ ----------\n" +
                "  -902386689";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionWithNegativeAndPositiveBiggerAbsAddends() {
        BigInteger firstAddend = new BigInteger("-798015");
        BigInteger secondAddend = new BigInteger("25260032677542");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "         -798015\n" +
                "  25260032677542\n" +
                "+ --------------\n" +
                "  25260031879527";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionTenThousandWithOne() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("1");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "      1\n" +
                "+ -----\n" +
                "  10001";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionTenThousandWithMinusOne() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("-1");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "     -1\n" +
                "+ -----\n" +
                "   9999";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionTenThousandWithMinus89() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("-89");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "    -89\n" +
                "+ -----\n" +
                "   9911";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionTenThousandWithMinus999() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("-999");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "   -999\n" +
                "+ -----\n" +
                "   9001";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintAndReturnZero() {
        BigInteger firstAddend = new BigInteger("1234567");
        BigInteger secondAddend = new BigInteger("-1234567");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "   1234567\n" +
                "  -1234567\n" +
                "+ --------\n" +
                "         0";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongAdditionTenThousandWithMinus9999() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("-9999");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "  -9999\n" +
                "+ -----\n" +
                "      1";
        assertEquals(expected, actual);

        expected = firstAddend.add(secondAddend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWithNullFirstAddend() {
        BigInteger secondAddend = BigInteger.valueOf(-812);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new IntegerAddition(null,  secondAddend));
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullSecondAddend() {
        BigInteger firstAddend = BigInteger.valueOf(-812);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new IntegerAddition(firstAddend, null));
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldReturnJson() {
        BigInteger firstAddend = new BigInteger("10000");
        BigInteger secondAddend = new BigInteger("-999");
        underTest = new IntegerAddition(firstAddend, secondAddend);
        String actual = underTest.toJson();
        String expected = "{\"sum\":\"9001\",\"steps\":[{\"digit\":1,\"memorized\":-1},{\"digit\":0,\"memorized\":-1},{\"digit\":0,\"memorized\":-1},{\"digit\":9,\"memorized\":-1},{\"digit\":0,\"memorized\":0}]}";
        assertEquals(expected, actual);
    }

}
