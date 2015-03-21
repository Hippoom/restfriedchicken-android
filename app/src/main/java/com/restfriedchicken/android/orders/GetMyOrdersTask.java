package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.RestfriedChickenApp;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GetMyOrdersTask extends GetCustomerResourceTask<MyOrdersRepresentation> {

    private String statusEq;

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback uiCallback) {
        this(application, uiCallback, "");
    }

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback<MyOrdersRepresentation> uiCallback, String statusEq) {
        super(application, uiCallback);
        this.statusEq = statusEq;
    }

    @Override
    protected MyOrdersRepresentation doInBackground(Void... params) {
        try {
            final String url = customerServiceBaseUrl() + "/1/orders?status=" + statusEq;
            MyOrdersRepresentation orders = getRestTemplate().getForObject(url, MyOrdersRepresentation.class);
            return orders;
        } catch (Exception e) {
            Log.e(getLogTag(), e.getMessage(), e);
        }
        return new MyOrdersRepresentation();
    }



}
