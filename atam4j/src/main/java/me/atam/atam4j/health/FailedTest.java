package me.atam.atam4j.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class FailedTest {

    private final String test;
    private final String cause;

    public FailedTest(@JsonProperty("test")String test,
                      @JsonProperty("cause")String cause) {
        this.test = test;
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public String getTest() {
        return test;
    }
}
