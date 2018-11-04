package ua.com.foxminded.integerdivision;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerDivisionTest {

    private static IntegerDivision underTest;
    private static final String DIVISION_BY_ZERO_MESSAGE = "Can't divide by zero.";
    private static final String NULL_ARGUMENT_MESSAGE = "One or more operands are null.";

    @Test
    void shouldPrintLongDivisionWithZeroDividend() {
        BigInteger dividend = BigInteger.ZERO;
        BigInteger divisor = BigInteger.valueOf(14);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_0 | 14\n" +
                " 0 |---\n" +
                " - | 0\n" +
                " 0";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithAbsDividendEqualsAbsDivisor() {
        BigInteger dividend = BigInteger.valueOf(-78);
        BigInteger divisor = BigInteger.valueOf(78);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_-78 | 78\n" +
                "  78 |----\n" +
                "  -- | -1\n" +
                "   0";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithDividendEqualDivisor() {
        BigInteger dividend = BigInteger.valueOf(14);
        BigInteger divisor = BigInteger.valueOf(14);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_14 | 14\n" +
                " 14 |---\n" +
                " -- | 1\n" +
                "  0";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithDivisorIsSubstringOfNegativeDividend() {
        BigInteger dividend = BigInteger.valueOf(-1234);
        BigInteger divisor = BigInteger.valueOf(123);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_-1234 | 123\n" +
                "  123  |-----\n" +
                "  ---  | -10\n" +
                "    -4";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithDivisorIsSubstringOfPositiveDividend() {
        BigInteger dividend = BigInteger.valueOf(123457);
        BigInteger divisor = BigInteger.valueOf(123);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_123457 | 123\n" +
                " 123    |------\n" +
                " ---    | 1003\n" +
                "   _457\n" +
                "    369\n" +
                "    ---\n" +
                "     88";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithAbsDividendLessThanAbsDivisor() {
        BigInteger dividend = BigInteger.valueOf(235);
        BigInteger divisor = BigInteger.valueOf(972);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_235 | 972\n" +
                "   0 |---\n" +
                "   - | 0\n" +
                " 235";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithDividendMuchMoreThanDivisor() {
        BigInteger dividend = BigInteger.valueOf(2071462334);
        BigInteger divisor = BigInteger.valueOf(9);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_2071462334 | 9\n" +
                " 18         |-----------\n" +
                " --         | 230162481\n" +
                " _27\n" +
                "  27\n" +
                "  --\n" +
                "   _14\n" +
                "     9\n" +
                "     -\n" +
                "    _56\n" +
                "     54\n" +
                "     --\n" +
                "     _22\n" +
                "      18\n" +
                "      --\n" +
                "      _43\n" +
                "       36\n" +
                "       --\n" +
                "       _73\n" +
                "        72\n" +
                "        --\n" +
                "        _14\n" +
                "          9\n" +
                "          -\n" +
                "          5";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithBigIntegers() {
        BigInteger dividend = new BigInteger("063782071731462398534");
        BigInteger divisor =  new BigInteger("7521654673");
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_63782071731462398534 | 7521654673\n" +
                " 60173237384          |------------\n" +
                " -----------          | 8479792612\n" +
                " _36088343474\n" +
                "  30086618692\n" +
                "  -----------\n" +
                "  _60017247826\n" +
                "   52651582711\n" +
                "   -----------\n" +
                "   _73656651152\n" +
                "    67694892057\n" +
                "    -----------\n" +
                "    _59617590953\n" +
                "     52651582711\n" +
                "     -----------\n" +
                "     _69660082429\n" +
                "      67694892057\n" +
                "      -----------\n" +
                "      _19651903728\n" +
                "       15043309346\n" +
                "       -----------\n" +
                "       _46085943825\n" +
                "        45129928038\n" +
                "        -----------\n" +
                "         _9560157873\n" +
                "          7521654673\n" +
                "          ----------\n" +
                "         _20385032004\n" +
                "          15043309346\n" +
                "          -----------\n" +
                "           5341722658";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithNegativeDividendAndPositiveDivisor() {
        BigInteger dividend = BigInteger.valueOf(-4257583);
        BigInteger divisor = BigInteger.valueOf(812);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_-4257583 | 812\n" +
                "  4060    |-------\n" +
                "  ----    | -5243\n" +
                "  _1975\n" +
                "   1624\n" +
                "   ----\n" +
                "   _3518\n" +
                "    3248\n" +
                "    ----\n" +
                "    _2703\n" +
                "     2436\n" +
                "     ----\n" +
                "     -267";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithPositiveDividendAndNegativeDivisor() {
        BigInteger dividend = BigInteger.valueOf(4257583);
        BigInteger divisor = BigInteger.valueOf(-812);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_4257583 | -812\n" +
                " 4060    |-------\n" +
                " ----    | -5243\n" +
                " _1975\n" +
                "  1624\n" +
                "  ----\n" +
                "  _3518\n" +
                "   3248\n" +
                "   ----\n" +
                "   _2703\n" +
                "    2436\n" +
                "    ----\n" +
                "     267";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithNegativeDividendAndNegativeDivisor() {
        BigInteger dividend = BigInteger.valueOf(-4257583);
        BigInteger divisor = BigInteger.valueOf(-812);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_-4257583 | -812\n" +
                "  4060    |------\n" +
                "  ----    | 5243\n" +
                "  _1975\n" +
                "   1624\n" +
                "   ----\n" +
                "   _3518\n" +
                "    3248\n" +
                "    ----\n" +
                "    _2703\n" +
                "     2436\n" +
                "     ----\n" +
                "     -267";
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongDivisionWithoutEndlessLoop() {
        BigInteger dividend = BigInteger.valueOf(42);
        BigInteger divisor = BigInteger.valueOf(2);
        underTest = new IntegerDivision(dividend, divisor);
        String actual = underTest.toString();
        String expected =
                "_42 | 2\n" +
                        " 4  |----\n" +
                        " -  | 21\n" +
                        " _2\n" +
                        "  2\n" +
                        "  -\n" +
                        "  0";
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWithNullDividend() {
        BigInteger divisor = BigInteger.valueOf(-812);
        underTest = new IntegerDivision(null,  divisor);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.calculate());
        assertEquals(NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullDivisor() {
        BigInteger dividend = BigInteger.valueOf(-812);
        underTest = new IntegerDivision(dividend, null);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.calculate());
        assertEquals(NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithZeroDivisor() {
        BigInteger dividend = BigInteger.valueOf(-813632);
        BigInteger divisor = BigInteger.ZERO;
        underTest = new IntegerDivision(dividend,  divisor);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.calculate());
        assertEquals(DIVISION_BY_ZERO_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldCalculateLongDivisionPositiveOperandsWithRemainder() {
        BigInteger dividend = BigInteger.valueOf(424567653);
        BigInteger divisor = BigInteger.valueOf(287964);
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
        BigInteger expectedQuotient = BigInteger.valueOf(1474);
        BigInteger expectedRemainder = BigInteger.valueOf(108717);
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionNegativeOperandsWithRemainder() {
        BigInteger dividend = new BigInteger("-890636708183627");
        BigInteger divisor = new BigInteger("-521546363");
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(1707684);
//        BigInteger expectedRemainder = BigInteger.valueOf(-328830335);
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionPositiveAndNegativeOperandsWithRemainder() {
        BigInteger dividend = BigInteger.valueOf(902357095);
        BigInteger divisor = BigInteger.valueOf(-902);
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(-1000395);
//        BigInteger expectedRemainder = BigInteger.valueOf(805);
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionNegativeAndPositiveOperandsWithRemainder() {
        BigInteger dividend = new BigInteger("-1450795000");
        BigInteger divisor = new BigInteger("876001");
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(-1656);
//        BigInteger expectedRemainder = BigInteger.valueOf(-137344);
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionPositiveAndNegativeOperandsWithoutRemainder() {
        BigInteger dividend = BigInteger.valueOf(589798760);
        BigInteger divisor = BigInteger.valueOf(-785);
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(-751336);
//        BigInteger expectedRemainder = BigInteger.ZERO;
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionNegativeAndPositiveOperandsWithoutRemainder() {
        BigInteger dividend = new BigInteger("-1450795000");
        BigInteger divisor = new BigInteger("1450795");
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(-1000);
//        BigInteger expectedRemainder = BigInteger.ZERO;
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionPositiveOperandsWithoutRemainder() {
        BigInteger dividend = new BigInteger("6569313653970");
        BigInteger divisor = BigInteger.valueOf(11497578);
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(571365);
//        BigInteger expectedRemainder = BigInteger.ZERO;
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }

    @Test
    void shouldCalculateLongDivisionNegativeOperandsWithoutRemainder() {
        BigInteger dividend = new BigInteger("-356136");
        BigInteger divisor = new BigInteger("-456");
        underTest = new IntegerDivision(dividend, divisor);
        IntegerDivision.DivisionResult actual = underTest.calculate();
//        BigInteger expectedQuotient = BigInteger.valueOf(781);
//        BigInteger expectedRemainder = BigInteger.ZERO;
        BigInteger expectedQuotient = dividend.divide(divisor);
        BigInteger expectedRemainder = dividend.subtract(divisor.multiply(expectedQuotient));
        BigInteger actualDividend = divisor.multiply(actual.getQuotient()).add(actual.getRemainder());
        assertEquals(expectedQuotient, actual.getQuotient());
        assertEquals(expectedRemainder, actual.getRemainder());
        assertEquals(dividend, actualDividend);
    }
}
