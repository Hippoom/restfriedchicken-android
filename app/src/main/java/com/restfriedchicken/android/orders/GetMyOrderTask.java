package com.restfriedchicken.android.orders;

import android.util.Log;

import com.restfriedchicken.android.RestfriedChickenApp;

public class GetMyOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {

    private String url;

    public GetMyOrderTask(RestfriedChickenApp application, UiCallback<MyOrderRepresentation> uiCallback, String url) {
        super(application, uiCallback);
        this.url = url;
    }

    @Override
    protected MyOrderRepresentation doInBackground(Void... params) {
        try {
            MyOrderRepresentation order = getRestTemplate().getForObject(url, MyOrderRepresentation.class);
            return order;
        } catch (Exception e) {
            Log.e(getLogTag(), e.getMessage(), e);
        }
        return new MyOrderRepresentation();
    }



}
