package me.atam.atam4j.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.ByteStreams;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ConfigLoader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private final Class<T> configClass;
    private final String configFileName;
    private final ObjectReader reader;
    private final StrSubstitutor substitutor;

    public ConfigLoader(Class<T> configClass, String configFileName) {
        this.configClass = configClass;
        this.configFileName = configFileName;
        this.reader = new ObjectMapper(new YAMLFactory()).reader(configClass);
        this.substitutor = new StrSubstitutor(new EnvironmentVariableLookup());
    }

    public ConfigLoader(Class<T> configClass, String configFileName, StrLookup<Object> stringLookup) {
        this.configClass = configClass;
        this.configFileName = configFileName;
        this.reader = new ObjectMapper(new YAMLFactory()).reader(configClass);
        this.substitutor = new StrSubstitutor(stringLookup);
    }

    public T getTestConfig() {
        try {
            LOGGER.info(String.format("Loading config from: %s", configFileName));
            final String substitutedString = substitutor.replace(new String(
                    ByteStreams.toByteArray(this.getClass().getClassLoader().getResourceAsStream(configFileName)),
                    StandardCharsets.UTF_8)
            );
            return reader.readValue(substitutedString);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public Class<T> getConfigClass() {
        return configClass;
    }
}
