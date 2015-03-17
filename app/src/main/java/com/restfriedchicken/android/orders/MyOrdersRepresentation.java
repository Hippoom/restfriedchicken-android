package com.restfriedchicken.android.orders;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersRepresentation {

    private List<String> orders = new ArrayList<>();

    private List<String> _links = new ArrayList<>();

    public List<String> getOrders() {
        return orders;
    }

    public List<String> get_links() {
        return _links;
    }
}
