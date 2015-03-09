package me.atam.atam4j.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private Class<T> configClass;
    private String configFileName;

    public ConfigLoader(Class<T> configClass, String configFileName) {
        this.configClass = configClass;
        this.configFileName = configFileName;
    }

    public T getTestConfig(){
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            LOGGER.info(String.format("Loading config from: %s", configFileName));
            return mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(configFileName), configClass);
        }
        catch(Exception e){
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
