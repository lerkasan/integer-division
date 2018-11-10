package ua.com.foxminded.integerdivision.math.multiplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.Result;

import java.util.List;

public class MultiplicationResult extends Result {
    @JsonProperty
    private String product;

    @JsonProperty
    private List<IntermediateMultiplicationResult> steps;

    public MultiplicationResult(String product) {
        this.product = product;
    }

    public MultiplicationResult(String product, List<IntermediateMultiplicationResult> steps) {
        this.product = product;
        this.steps = steps;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<IntermediateMultiplicationResult> getSteps() {
        return steps;
    }

    public void setSteps(List<IntermediateMultiplicationResult> steps) {
        this.steps = steps;
    }
}
