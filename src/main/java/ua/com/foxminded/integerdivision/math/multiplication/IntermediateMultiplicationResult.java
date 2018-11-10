package ua.com.foxminded.integerdivision.math.multiplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.Result;

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