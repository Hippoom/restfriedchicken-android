package com.restfriedchicken.android.orders;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersRepresentation {

    private List<MyOrderRepresentation> orders = new ArrayList<>();

    public List<MyOrderRepresentation> getOrders() {
        return orders;
    }
}
