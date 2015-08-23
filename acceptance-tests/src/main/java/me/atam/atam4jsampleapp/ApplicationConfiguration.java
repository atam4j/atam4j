package me.atam.atam4jsampleapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {

    @JsonProperty
    private Class[] testClasses;

    public Class[] getTestClasses(){
        return testClasses;
    }
}
