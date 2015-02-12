package me.atam.atam4j.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private Class<T> configClass;

    public ConfigLoader(Class<T> configClass) {
        this.configClass = configClass;
    }

    public T getTestConfig(){
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            String filename = getFileName();
            LOGGER.info(String.format("Loading config from: %s", filename));
            return mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(filename), configClass);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private String getFileName() {
        return "default-test-config.yml";
    }
}