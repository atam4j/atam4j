package me.atam.atam4jsampleapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {

    @JsonProperty
    private int initialDelayInMillis;

    @JsonProperty
    private Class[] testClasses;
    private long periodInMillis;

    public Class[] getTestClasses(){
        return testClasses;
    }

    public int getInitialDelayInMillis() {
        return initialDelayInMillis;
    }

    public long getPeriodInMillis() {
        return periodInMillis;
    }
}
