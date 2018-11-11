package ua.com.foxminded.integerdivision.math.operation.multiplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.operation.Result;

import java.math.BigInteger;
import java.util.List;

public class MultiplicationResult extends Result {
    @JsonProperty
    private BigInteger product;

    @JsonProperty
    private List<IntermediateMultiplicationResult> steps;

    public MultiplicationResult(BigInteger product) {
        this.product = product;
    }

    public MultiplicationResult(BigInteger product, List<IntermediateMultiplicationResult> steps) {
        this.product = product;
        this.steps = steps;
    }

    public BigInteger getProduct() {
        return product;
    }

    public void setProduct(BigInteger product) {
        this.product = product;
    }

    public List<IntermediateMultiplicationResult> getSteps() {
        return steps;
    }

    public void setSteps(List<IntermediateMultiplicationResult> steps) {
        this.steps = steps;
    }
}
