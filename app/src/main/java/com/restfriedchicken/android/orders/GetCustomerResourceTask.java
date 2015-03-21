package com.restfriedchicken.android.orders;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.RestfriedChickenApp;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class GetCustomerResourceTask<Result> extends AsyncTask<Void, Void, Result> {


    private RestfriedChickenApp application;

    private UiCallback uiCallback;

    protected GetCustomerResourceTask(RestfriedChickenApp application, UiCallback uiCallback) {
        this.application = application;
        this.uiCallback = uiCallback;
    }

    protected String getLogTag() {
        return "DisplayMyOrdersActivity";
    }

    protected ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Override
    protected void onPostExecute(Result result) {
        uiCallback.handle(result);
    }

    protected RestTemplate getRestTemplate() {
        return getRestTemplate(jsonMessageConverter(objectMapper()));
    }
    protected String customerServiceBaseUrl() {
        return application.customerServiceBaseUrl();
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

    public static abstract class UiCallback<Resource> {

        public abstract void handle(Resource orders);
    }
}
