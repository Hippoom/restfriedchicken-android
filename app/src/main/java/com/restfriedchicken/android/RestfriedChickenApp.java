package com.restfriedchicken.android;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.restfriedchicken.android.http.HttpModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import dagger.ObjectGraph;

public class RestfriedChickenApp extends Application {

    private Properties config;
    private ObjectGraph graph;

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
        this.graph = ObjectGraph.create(new HttpModule());
    }

    public void inject(Object object) {
        graph.inject(object);
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
