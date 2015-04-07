package com.restfriedchicken.android;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.restfriedchicken.rest.ResourceProvider;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnResource;
import com.restfriedchicken.rest.orders.OrderResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestfriedChickenApp extends Application {

    private Properties config;
    private ResourceProvider resourceProvider;

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
        resourceProvider = new ResourceProvider(config);

    }

    public OrderResource provideOrderResource() {
        return resourceProvider.provideObjectResource();
    }

    public OnlineTxnResource provideOnlineTxnResource() {
        return resourceProvider.provideOnlineTxnResource();
    }
}
