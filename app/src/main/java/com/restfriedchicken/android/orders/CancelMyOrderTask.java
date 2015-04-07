package com.restfriedchicken.android.orders;

import android.os.AsyncTask;
import android.util.Log;

import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.OrderRepresentation;
import com.restfriedchicken.rest.orders.OrderResource;

public class CancelMyOrderTask extends AsyncTask<Void, Void, OrderRepresentation> {
    private final OrderResource orderResource;
    private final Link cancel;
    private final UiCallback<OrderRepresentation> uiCallback;

    public CancelMyOrderTask(OrderResource orderResource, Link cancel, UiCallback<OrderRepresentation> uiCallback) {
        this.orderResource = orderResource;
        this.cancel = cancel;
        this.uiCallback = uiCallback;
    }

    @Override
    protected OrderRepresentation doInBackground(Void... params) {
        try {
            return orderResource.cancel(cancel);
        } catch (Exception e) {
            Log.e("CancelMyOrderTask", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(OrderRepresentation result) {
        this.uiCallback.handle(result);
    }


}
