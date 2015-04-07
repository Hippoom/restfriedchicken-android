package com.restfriedchicken.android.orders;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

public class CancelMyOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {
    private Link link;

    CancelMyOrderTask(RestfriedChickenApp app, UiCallback<MyOrderRepresentation> uiCallback, Link link) {
        super(app, uiCallback);
        this.link = link;
    }

    @Override
    protected MyOrderRepresentation doInBackground(Void... params) {
        try {
            String order = HttpRequest.delete(link.getHref()).body();

            return objectMapper().readValue(order, MyOrderRepresentation.class);
        } catch (Exception e) {
            Log.e("CancelMyOrderTask", e.getMessage(), e);
            return null;
        }
    }



}
