package com.restfriedchicken.android.orders;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.RestfriedChickenApp;

import javax.inject.Inject;

public abstract class GetCustomerResourceTask<Result> extends AsyncTask<Void, Void, Result> {
    @Inject
    ObjectMapper objectMapper;

    private RestfriedChickenApp application;

    private UiCallback uiCallback;

    protected GetCustomerResourceTask(RestfriedChickenApp application, UiCallback uiCallback) {
        this.application = application;
        this.uiCallback = uiCallback;
        this.application.inject(this);
    }

    protected String getLogTag() {
        return "DisplayMyOrdersActivity";
    }


    @Override
    protected void onPostExecute(Result result) {
        uiCallback.handle(result);
    }

    protected String customerServiceBaseUrl() {
        return application.customerServiceBaseUrl();
    }

    protected ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static abstract class UiCallback<Resource> {

        public abstract void handle(Resource orders);
    }
}
