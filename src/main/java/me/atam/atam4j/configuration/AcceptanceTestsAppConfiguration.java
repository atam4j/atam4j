package me.atam.atam4j.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * @author anuragkapur
 */
public class AcceptanceTestsAppConfiguration extends Configuration implements AcceptanceTestsConfiguration {

    @JsonProperty
    private String helloWorldMessage;

    public String getHelloWorldMessage() {
        return helloWorldMessage;
    }

    public void setHelloWorldMessage(String helloWorldMessage) {
        this.helloWorldMessage = helloWorldMessage;
    }
}
