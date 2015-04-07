package com.restfriedchicken.android.orders;

import android.os.AsyncTask;
import android.util.Log;

import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.OrderRepresentation;
import com.restfriedchicken.rest.orders.OrderResource;

import java.io.IOException;

public class EditOrderTask extends AsyncTask<Void, Void, OrderRepresentation> {
    private OrderResource orderResource;
    private EditOrderCommand command;
    private Link link;
    private UiCallback<OrderRepresentation> uiCallback;

    EditOrderTask(OrderResource orderResource, Link link, EditOrderCommand command, UiCallback<OrderRepresentation> uiCallback) {
        this.orderResource = orderResource;
        this.command = command;
        this.link = link;
        this.uiCallback = uiCallback;
    }

    @Override
    protected OrderRepresentation doInBackground(Void... params) {
        try {
            return orderResource.edit(link, command);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(OrderRepresentation result) {
        this.uiCallback.handle(result);
    }
}
