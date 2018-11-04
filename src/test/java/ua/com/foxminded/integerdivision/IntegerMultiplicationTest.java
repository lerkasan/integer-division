package ua.com.foxminded.integerdivision;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerMultiplicationTest {

    private IntegerMultiplication underTest;

    @Test
    void shouldPrintLongMultiplicationWithZeroMultiplicand() {
        BigInteger multiplicand = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.valueOf(14);
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "   0\n" +
                "  14\n" +
                "* --\n" +
                "   0";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithZeroMultiplier() {
        BigInteger multiplicand = BigInteger.valueOf(-235436);
        BigInteger multiplier = BigInteger.ZERO;
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "  -235436\n" +
                "        0\n" +
                "* -------\n" +
                "        0";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithOneMultiplier() {
        BigInteger multiplicand = BigInteger.valueOf(-235436);
        BigInteger multiplier = BigInteger.ONE;
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "  -235436\n" +
                "        1\n" +
                "* -------\n" +
                "  -235436";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithBPositiveOperands() {
        BigInteger multiplicand = BigInteger.valueOf(865467669);
        BigInteger multiplier = BigInteger.valueOf(13526574);
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "          865467669\n" +
                "           13526574\n" +
                "        * ---------\n" +
                "         3461870676\n" +
                "        6058273683\n" +
                "       4327338345\n" +
                "      5192806014\n" +
                "     1730935338\n" +
                "    4327338345\n" +
                "   2596403007\n" +
                "   865467669\n" +
                "+ -----------------\n" +
                "  11706812469336006";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithNegativeAndPositiveOperands() {
        BigInteger multiplicand = new BigInteger("-723853632645632");
        BigInteger multiplier = new BigInteger("814325621436437516224");
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "                       -723853632645632\n" +
                "                  814325621436437516224\n" +
                "                * ---------------------\n" +
                "                       2895414530582528\n" +
                "                      1447707265291264\n" +
                "                     1447707265291264\n" +
                "                    4343121795873792\n" +
                "                    723853632645632\n" +
                "                  3619268163228160\n" +
                "                 5066975428519424\n" +
                "                2171560897936896\n" +
                "               2895414530582528\n" +
                "              4343121795873792\n" +
                "             2171560897936896\n" +
                "            2895414530582528\n" +
                "            723853632645632\n" +
                "          1447707265291264\n" +
                "         4343121795873792\n" +
                "        3619268163228160\n" +
                "       1447707265291264\n" +
                "      2171560897936896\n" +
                "     2895414530582528\n" +
                "     723853632645632\n" +
                "   5790829061165056\n" +
                "+ -------------------------------------\n" +
                "  -589452559233177032879051352042733568";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithPostiveAndNegativeOperands() {
        BigInteger multiplicand = new BigInteger("1530069200");
        BigInteger multiplier = new BigInteger("-67090");
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "        1530069200\n" +
                "            -67090\n" +
                "      * ----------\n" +
                "      13770622800\n" +
                "    10710484400\n" +
                "    9180415200\n" +
                "+ ----------------\n" +
                "  -102652342628000";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithNegativeOperands() {
        BigInteger multiplicand = new BigInteger("-19071359832");
        BigInteger multiplier = new BigInteger("-789");
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "    -19071359832\n" +
                "            -789\n" +
                "  * ------------\n" +
                "    171642238488\n" +
                "   152570878656\n" +
                "  133499518824\n" +
                "+ --------------\n" +
                "  15047302907448";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintLongMultiplicationWithoutZeroLines() {
        BigInteger multiplicand = new BigInteger("12340005600789");
        BigInteger multiplier = new BigInteger("980076500004300");
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toString();
        String expected =
                "                 12340005600789\n" +
                "                980076500004300\n" +
                "              * ---------------\n" +
                "               37020016802367\n" +
                "              49360022403156\n" +
                "         61700028003945\n" +
                "        74040033604734\n" +
                "       86380039205523\n" +
                "    98720044806312\n" +
                "  111060050407101\n" +
                "+ -----------------------------\n" +
                "  12094149499254742382583392700";
        assertEquals(expected, actual);

        expected = multiplicand.multiply(multiplier).toString();
        actual = underTest.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWithNullMultiplicand() {
        BigInteger multiplier = BigInteger.valueOf(-812);
        underTest = new IntegerMultiplication(null,  multiplier);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.calculate());
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullMultiplier() {
        BigInteger multiplicand = BigInteger.valueOf(-812);
        underTest = new IntegerMultiplication(multiplicand,  null);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> underTest.calculate());
        assertEquals(Operation.NULL_ARGUMENT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldReturnJson() {
        BigInteger multiplicand = BigInteger.valueOf(142);
        BigInteger multiplier = BigInteger.valueOf(23);
        underTest = new IntegerMultiplication(multiplicand, multiplier);
        String actual = underTest.toJson();
        String expected = "{\"product\":\"3266\",\"steps\":[{\"addend\":\"426\"},{\"addend\":\"284\"}]}";
        assertEquals(expected, actual);
    }
}
