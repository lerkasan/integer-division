package ua.com.foxminded.integerdivision.math;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {

    @JsonIgnore
    protected int rearIndex;

    public int getRearIndex() {
        return rearIndex;
    }

    public void setRearIndex(int rearIndex) {
        this.rearIndex = rearIndex;
    }
}
