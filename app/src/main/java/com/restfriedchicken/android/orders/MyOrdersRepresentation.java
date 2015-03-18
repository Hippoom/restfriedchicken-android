package com.restfriedchicken.android.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyOrdersRepresentation {

    private List<MyOrderRepresentation> orders = new ArrayList<>();

    public List<MyOrderRepresentation> getOrders() {
        return orders;
    }
}
