package com.restfriedchicken.android.orders;

import android.os.AsyncTask;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.OrderRepresentation;
import com.restfriedchicken.rest.orders.OrderResource;
import com.restfriedchicken.rest.orders.OrdersRepresentation;

public class GetMyOrderTask extends AsyncTask<Void, Void, OrderRepresentation> {
    private OrderResource orderResource;
    private Link link;
    private UiCallback uiCallback;

    public GetMyOrderTask(OrderResource orderResource, Link link, UiCallback uiCallback) {
        this.orderResource = orderResource;
        this.link = link;
        this.uiCallback = uiCallback;
    }

    @Override
    protected OrderRepresentation doInBackground(Void... params) {
        try {
            return orderResource.find(link);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
        return new OrderRepresentation();
    }

    @Override
    protected void onPostExecute(OrderRepresentation result) {
        this.uiCallback.handle(result);
    }

}
