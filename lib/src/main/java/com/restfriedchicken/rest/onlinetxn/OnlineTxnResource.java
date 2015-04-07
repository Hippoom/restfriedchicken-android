package com.restfriedchicken.rest.onlinetxn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.OrderRepresentation;
import com.restfriedchicken.rest.orders.OrdersRepresentation;

import java.io.IOException;


public class OnlineTxnResource {
    private ObjectMapper objectMapper;

    public OnlineTxnResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OnlineTxnRepresentation create(Link link, MakeOnlineTxnCommand command) throws IOException {
        String body = objectMapper.writeValueAsString(command);
        String onlineTxn = HttpRequest.post(link.getHref()).send(body).body();
        return objectMapper.readValue(onlineTxn, OnlineTxnRepresentation.class);
    }
}