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

public class GetMyOrdersTask extends AsyncTask<Void, Void, MyOrdersRepresentation> {

    private String statusEq;

    private RestfriedChickenApp application;

    private UiCallback uiCallback;

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback uiCallback) {
        this(application, uiCallback, "");
    }

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback uiCallback, String statusEq) {
        this.statusEq = statusEq;
        this.application = application;
        this.uiCallback = uiCallback;
    }

    @Override
    protected MyOrdersRepresentation doInBackground(Void... params) {
        try {
            final String url = customerServiceBaseUrl() + "/1/orders?status=" + statusEq;
            MyOrdersRepresentation orders = getRestTemplate(jsonMessageConverter(objectMapper())).getForObject(url, MyOrdersRepresentation.class);
            return orders;
        } catch (Exception e) {
            Log.e("DisplayMyOrdersActivity", e.getMessage(), e);
        }
        return new MyOrdersRepresentation();
    }

    private String customerServiceBaseUrl() {
        return application.customerServiceBaseUrl();
    }

    @Override
    protected void onPostExecute(MyOrdersRepresentation orders) {
        uiCallback.handle(orders);
    }

    private RestTemplate getRestTemplate(MappingJackson2HttpMessageConverter converter) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static abstract class UiCallback {

        public abstract void handle(MyOrdersRepresentation orders);
    }
}
