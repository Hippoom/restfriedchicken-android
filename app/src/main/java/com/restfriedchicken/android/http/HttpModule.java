package com.restfriedchicken.android.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnResource;
import com.restfriedchicken.rest.orders.OrderResource;

import java.util.Properties;

public class HttpModule {

    private Properties config;

    public HttpModule(Properties config) {
        this.config = config;
    }

    public String customerServiceBaseUrl() {
        String value = config.getProperty("customerServiceBaseUrl");
        if (value == null || value.trim().equals("")) {
            return "http://www.restfriedchicken.com/customers";
        } else {
            return value;
        }
    }

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public OrderResource provideObjectResource() {
        return new OrderResource(customerServiceBaseUrl(), objectMapper());
    }

    public OnlineTxnResource provideOnlineTxnResource() {
        return new OnlineTxnResource(objectMapper());
    }
}
