package com.restfriedchicken.android.orders;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.orders.MyOrdersRepresentation;

public class GetMyOrdersTask extends GetCustomerResourceTask<MyOrdersRepresentation> {

    private String statusEq;

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback uiCallback) {
        this(application, uiCallback, "");
    }

    public GetMyOrdersTask(RestfriedChickenApp application, UiCallback<MyOrdersRepresentation> uiCallback, String statusEq) {
        super(application, uiCallback);
        this.statusEq = statusEq;
    }

    @Override
    protected MyOrdersRepresentation doInBackground(Void... params) {
        try {
            final String url = customerServiceBaseUrl() + "/1/orders?status=" + statusEq;

            String orders = HttpRequest.get(url).body();

            return objectMapper().readValue(orders, MyOrdersRepresentation.class);
        } catch (Exception e) {
            Log.e(getLogTag(), e.getMessage(), e);
        }
        return new MyOrdersRepresentation();
    }



}
