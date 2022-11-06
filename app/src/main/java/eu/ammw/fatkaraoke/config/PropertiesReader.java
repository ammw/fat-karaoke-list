package eu.ammw.fatkaraoke.config;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final String TAG = "FKA PROPRDR";
    private static final String DEFAULT_PROPS = "application.properties";

    private PropertiesReader() {}

    public static Properties readProperties(Context context) {
        return readProperties(context, DEFAULT_PROPS);
    }

    public static Properties readProperties(Context context, String fileName) {
        Properties properties = new Properties();
        try {
            AssetManager am = context.getAssets();
            InputStream inputStream = am.open(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        return properties;
    }
}
