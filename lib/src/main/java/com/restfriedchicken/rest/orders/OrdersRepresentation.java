package com.restfriedchicken.rest.orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersRepresentation {

    private List<OrderRepresentation> orders = new ArrayList<OrderRepresentation>();

    public List<OrderRepresentation> getOrders() {
        return orders;
    }
}
