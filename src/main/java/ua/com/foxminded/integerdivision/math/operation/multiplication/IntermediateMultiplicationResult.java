package ua.com.foxminded.integerdivision.math.operation.multiplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.operation.Result;

public class IntermediateMultiplicationResult extends Result {

    @JsonProperty
    private String addend;

    public String getAddend() {
        return addend;
    }

    public void setAddend(String addend) {
        this.addend = addend;
    }
}