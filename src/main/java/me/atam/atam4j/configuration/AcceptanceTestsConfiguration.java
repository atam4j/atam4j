package me.atam.atam4j.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author anuragkapur
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public interface AcceptanceTestsConfiguration {

    public String getHelloWorldMessage();

    public void setHelloWorldMessage(String helloWorldMessage);
}
