package eu.ammw.fatkaraoke.config;

import android.content.Context;

import java.util.Properties;

import javax.inject.Inject;

public class Configuration {
    private final Properties properties;

    @Inject
    public Configuration(Context context) {
        this.properties = PropertiesReader.readProperties(context);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
