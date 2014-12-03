package me.atam.atam4j.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anuragkapur
 */
public class AcceptanceTestsBuildConfiguration implements AcceptanceTestsConfiguration {

    @JsonProperty
    private String helloWorldMessage;

    @Override
    public String getHelloWorldMessage() {
        return helloWorldMessage;
    }

    @Override
    public void setHelloWorldMessage(String helloWorldMessage) {
        this.helloWorldMessage = helloWorldMessage;
    }
}
