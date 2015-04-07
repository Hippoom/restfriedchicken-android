package com.restfriedchicken.android;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.restfriedchicken.android.http.HttpModule;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnResource;
import com.restfriedchicken.rest.orders.OrderResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestfriedChickenApp extends Application {

    private Properties config;
    private HttpModule httpModule;

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
        httpModule = new HttpModule(config);

    }

    public OrderResource provideOrderResource() {
        return httpModule.provideObjectResource();
    }

    public OnlineTxnResource provideOnlineTxnResource() {
        return httpModule.provideOnlineTxnResource();
    }
}
