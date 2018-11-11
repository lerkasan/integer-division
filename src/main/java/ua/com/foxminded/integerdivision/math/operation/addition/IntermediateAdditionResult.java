package ua.com.foxminded.integerdivision.math.operation.addition;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.foxminded.integerdivision.math.operation.Result;

public class IntermediateAdditionResult extends Result {
    @JsonProperty
    private int digit;

    @JsonProperty
    private int memorized;

    public int getDigit() {
        return digit;
    }

    public int getMemorized() {
        return memorized;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public void setMemorized(int memorized) {
        this.memorized = memorized;
    }
}
