package me.atam.atam4jsampleapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import me.atam.atam4j.Atam4j;

public class ApplicationConfiguration extends Configuration {

    @JsonProperty
    private int initialDelayInMillis;

    @JsonProperty
    private Class[] testClasses;

    public Class[] getTestClasses(){
        return testClasses;
    }

    public int getInitialDelayInMillis() {
        return initialDelayInMillis;
    }
}
