package ua.com.foxminded.integerdivision.math.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.Result;

import java.math.BigInteger;
import java.util.List;

public class DivisionResult extends Result {
    @JsonProperty
    private BigInteger quotient;

    @JsonProperty
    private BigInteger remainder;

    @JsonProperty
    private List<IntermediateDivisionResult> steps;

    public DivisionResult() {}

    public DivisionResult(BigInteger quotient, BigInteger remainder, List<IntermediateDivisionResult> steps) {
        this.quotient = quotient;
        this.remainder = remainder;
        this.steps = steps;
    }

    public BigInteger getQuotient() {
        return quotient;
    }

    public BigInteger getRemainder() {
        return remainder;
    }

    public List<IntermediateDivisionResult> getSteps() {
        return steps;
    }

    public void setQuotient(BigInteger quotient) {
        this.quotient = quotient;
    }

    public void setRemainder(BigInteger remainder) {
        this.remainder = remainder;
    }

    public void setSteps(List<IntermediateDivisionResult> steps) {
        this.steps = steps;
    }
}
