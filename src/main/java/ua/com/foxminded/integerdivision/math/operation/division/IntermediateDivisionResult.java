package ua.com.foxminded.integerdivision.math.operation.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.operation.Result;

import java.math.BigInteger;

public class IntermediateDivisionResult extends Result {
    @JsonProperty
    private BigInteger minuend;

    @JsonProperty
    private BigInteger subtrahend;

    @JsonProperty
    private BigInteger difference;

    @JsonProperty
    private BigInteger quotient;


    public IntermediateDivisionResult() {
        this.minuend = BigInteger.ZERO;
        this.subtrahend = BigInteger.ZERO;
        this.difference = BigInteger.ZERO;
        this.quotient = BigInteger.ZERO;
        this.rearIndex = 0;
    }

    public IntermediateDivisionResult(BigInteger minuend, BigInteger subtrahend, BigInteger difference, BigInteger quotient, int rearIndex) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
        this.difference = difference;
        this.quotient = quotient;
        this.rearIndex = rearIndex;
    }

    public BigInteger getMinuend() {
        return minuend;
    }

    public void setMinuend(BigInteger minuend) {
        this.minuend = minuend;
    }

    public BigInteger getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(BigInteger subtrahend) {
        this.subtrahend = subtrahend;
    }

    public BigInteger getDifference() {
        return difference;
    }

    public void setDifference(BigInteger difference) {
        this.difference = difference;
    }

    public BigInteger getQuotient() {
        return quotient;
    }

    public void setQuotient(BigInteger quotient) {
        this.quotient = quotient;
    }
}
