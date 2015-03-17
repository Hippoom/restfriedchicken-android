package com.restfriedchicken.android;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestfriedChickenApp extends Application {

    private Properties config;

    @Override
    public void onCreate() {
        super.onCreate();

        Resources resources = this.getResources();
        AssetManager assetManager = resources.getAssets();

        config = new Properties();
        try {
            InputStream inputStream = assetManager.open("config.properties");
            config.load(inputStream);
        } catch (IOException e) {
            Log.e("RestfriedChickenApp", e.getMessage(), e);
        }
    }

    public String customerServiceBaseUrl() {
        String value = config.getProperty("customerServiceBaseUrl");
        if (value == null || value.trim().equals("")) {
            return "http://www.restfriedchicken.com/customers";
        } else {
            return value;
        }
    }


}
