package com.restfriedchicken.rest.orders;

import com.restfriedchicken.rest.orders.MyOrderRepresentation;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersRepresentation {

    private List<MyOrderRepresentation> orders = new ArrayList<MyOrderRepresentation>();

    public List<MyOrderRepresentation> getOrders() {
        return orders;
    }
}
