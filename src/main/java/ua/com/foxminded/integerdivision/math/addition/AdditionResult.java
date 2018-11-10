package ua.com.foxminded.integerdivision.math.addition;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.Result;

import java.math.BigInteger;
import java.util.List;

public class AdditionResult extends Result {
    @JsonProperty
    private BigInteger sum;

    @JsonProperty
    private List<IntermediateAdditionResult> steps;

    public AdditionResult() {
    }

    public AdditionResult(BigInteger sum) {
        this.sum = sum;
    }

    public AdditionResult(BigInteger sum, List<IntermediateAdditionResult> steps) {
        this.sum = sum;
        this.steps = steps;
    }

    public BigInteger getSum() {
        return sum;
    }

    public List<IntermediateAdditionResult> getSteps() {
        return steps;
    }
}