package ua.com.foxminded.integerdivision.math.addition;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.Result;

import java.util.List;

public class AdditionResult extends Result {
    @JsonProperty
    private String sum;

    @JsonProperty
    private List<IntermediateAdditionResult> steps;

    public AdditionResult() {
    }

    public AdditionResult(String sum) {
        this.sum = sum;
    }

    public AdditionResult(String sum, List<IntermediateAdditionResult> steps) {
        this.sum = sum;
        this.steps = steps;
    }

    //my TODO make sum BigInteger!!!
    public String getSum() {
        return sum;
    }

    public List<IntermediateAdditionResult> getSteps() {
        return steps;
    }
}