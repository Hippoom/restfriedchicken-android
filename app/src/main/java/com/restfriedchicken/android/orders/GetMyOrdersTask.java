package com.restfriedchicken.android.orders;

import android.os.AsyncTask;
import android.util.Log;

import com.restfriedchicken.rest.orders.OrderResource;
import com.restfriedchicken.rest.orders.OrdersRepresentation;

public class GetMyOrdersTask extends AsyncTask<Void, Void, OrdersRepresentation> {
    private OrderResource orderResource;
    private String statusEq;
    private UiCallback uiCallback;

    public GetMyOrdersTask(OrderResource orderResource, String statusEq, UiCallback uiCallback) {
        this.orderResource = orderResource;
        this.statusEq = statusEq;
        this.uiCallback = uiCallback;
    }

    @Override
    protected OrdersRepresentation doInBackground(Void... params) {
        try {
            return orderResource.find(statusEq);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
        return new OrdersRepresentation();
    }

    @Override
    protected void onPostExecute(OrdersRepresentation result) {
        this.uiCallback.handle(result);
    }

}
