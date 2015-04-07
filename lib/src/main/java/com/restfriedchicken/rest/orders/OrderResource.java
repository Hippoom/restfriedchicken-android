package com.restfriedchicken.rest.orders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.rest.Link;

import java.io.IOException;


public class OrderResource {
    private String baseUrl;
    private ObjectMapper objectMapper;

    public OrderResource(String baseUrl, ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    public OrdersRepresentation find(String statusEq) throws IOException {
        final String url = baseUrl + "/1/orders?status=" + statusEq;
        String orders = HttpRequest.get(url).body();
        return objectMapper.readValue(orders, OrdersRepresentation.class);
    }

    public OrderRepresentation cancel(Link link) throws IOException {
        String order = HttpRequest.delete(link.getHref()).body();
        return objectMapper.readValue(order, OrderRepresentation.class);
    }

    public OrderRepresentation edit(Link link, EditOrderCommand command) throws IOException {
        String requestBody = objectMapper.writeValueAsString(command);
        String responseBody = HttpRequest.put(link.getHref()).send(requestBody).body();
        return objectMapper.readValue(responseBody, OrderRepresentation.class);
    }


    public OrderRepresentation find(Link link) throws IOException {
        String body = HttpRequest.get(link.getHref()).body();
        OrderRepresentation order =  objectMapper.readValue(body, OrderRepresentation.class);
        return order;
    }
}