package com.restfriedchicken.android.orders;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

public class GetMyOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {

    private String url;

    public GetMyOrderTask(RestfriedChickenApp application, UiCallback<MyOrderRepresentation> uiCallback, String url) {
        super(application, uiCallback);
        this.url = url;
    }

    @Override
    protected MyOrderRepresentation doInBackground(Void... params) {
        try {
            String body = HttpRequest.get(url).body();
            MyOrderRepresentation order =  objectMapper().readValue(body, MyOrderRepresentation.class);
            return order;
        } catch (Exception e) {
            Log.e(getLogTag(), e.getMessage(), e);
        }
        return new MyOrderRepresentation();
    }



}
