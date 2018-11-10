package ua.com.foxminded.integerdivision.math;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.integerdivision.math.subtraction.IntegerSubtraction;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerSubtractionTest {

    private IntegerSubtraction underTest;

    @Test
    void shouldPrintLongSubtractionWithZerominuend() {
        BigInteger minuend = BigInteger.ZERO;
        BigInteger subtrahend = BigInteger.valueOf(1454360);
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "         0\n" +
                "   1454360\n" +
                "- --------\n" +
                "  -1454360";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithZerosubtrahend() {
        BigInteger minuend = new BigInteger("-4654490235436700265");
        BigInteger subtrahend = BigInteger.ZERO;
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  -4654490235436700265\n" +
                        "                     0\n" +
                        "- --------------------\n" +
                        "  -4654490235436700265";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithPositiveOperands() {
        BigInteger minuend = new BigInteger("142390237823005320");
        BigInteger subtrahend = new BigInteger("909235809253");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  142390237823005320\n" +
                "        909235809253\n" +
                "- ------------------\n" +
                "  142389328587196067";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithNegativeOperands() {
        BigInteger minuend = new BigInteger("-2389237895322306");
        BigInteger subtrahend = new BigInteger("-9823920");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  -2389237895322306\n" +
                "           -9823920\n" +
                "- -----------------\n" +
                "  -2389237885498386";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithPositiveBiggerAndNegativeOperands() {
        BigInteger minuend = new BigInteger("325643612070025");
        BigInteger subtrahend = new BigInteger("-548062382");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  325643612070025\n" +
                "       -548062382\n" +
                "- ---------------\n" +
                "  325644160132407";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithPositiveAndNegativeBiggerAbsOperands() {
        BigInteger minuend = new BigInteger("20749823");
        BigInteger subtrahend = new BigInteger("-970312576003086");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "          20749823\n" +
                "  -970312576003086\n" +
                "- ----------------\n" +
                "   970312596752909";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithNegativeBiggerAbsAndPositiveOperands() {
        BigInteger minuend = new BigInteger("-902387923");
        BigInteger subtrahend = new BigInteger("1234");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  -902387923\n" +
                "        1234\n" +
                "- ----------\n" +
                "  -902389157";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionWithNegativeAndPositiveBiggerAbsOperands() {
        BigInteger minuend = new BigInteger("-798015");
        BigInteger subtrahend = new BigInteger("25260032677542");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "          -798015\n" +
                "   25260032677542\n" +
                "- ---------------\n" +
                "  -25260033475557";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionTenThousandWithOne() {
        BigInteger minuend = new BigInteger("10000");
        BigInteger subtrahend = new BigInteger("1");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "      1\n" +
                "- -----\n" +
                "   9999";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionTenThousandWithMinusOne() {
        BigInteger minuend = new BigInteger("10000");
        BigInteger subtrahend = new BigInteger("-1");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "     -1\n" +
                "- -----\n" +
                "  10001";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionTenThousandWithMinus89() {
        BigInteger minuend = new BigInteger("10000");
        BigInteger subtrahend = new BigInteger("-89");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "    -89\n" +
                "- -----\n" +
                "  10089";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionTenThousandWithMinus999() {
        BigInteger minuend = new BigInteger("10000");
        BigInteger subtrahend = new BigInteger("-999");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "   -999\n" +
                "- -----\n" +
                "  10999";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongSubtractionTenThousandWithMinus9999() {
        BigInteger minuend = new BigInteger("10000");
        BigInteger subtrahend = new BigInteger("-9999");
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toString();
        String expected =
                "  10000\n" +
                "  -9999\n" +
                "- -----\n" +
                "  19999";
        assertEquals(expected, actual);

        expected = minuend.subtract(subtrahend).toString();
        actual = underTest.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWithNullminuend() {
        BigInteger subtrahend = BigInteger.valueOf(-812);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new IntegerSubtraction(null,  subtrahend));
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Disabled
    @Test
    void shouldThrowExceptionWithNullsubtrahend() {
        BigInteger minuend = BigInteger.valueOf(-812);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new IntegerSubtraction(minuend, null));
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldReturnJson() {
        BigInteger minuend = BigInteger.valueOf(142);
        BigInteger subtrahend = BigInteger.valueOf(23);
        underTest = new IntegerSubtraction(minuend, subtrahend);
        String actual = underTest.toJson();
        String expected = "{\"sum\":\"119\",\"steps\":[{\"digit\":9,\"memorized\":-1},{\"digit\":1,\"memorized\":0},{\"digit\":1,\"memorized\":0}]}";
        assertEquals(expected, actual);
    }
}
