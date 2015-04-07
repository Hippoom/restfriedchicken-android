package com.restfriedchicken.android.orders;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

public class EditOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {
    private DisplayMyOrderActivity caller;
    private EditOrderCommand command;
    private String link;

    EditOrderTask(RestfriedChickenApp app, UiCallback<MyOrderRepresentation> uiCallback, DisplayMyOrderActivity caller, String link, EditOrderCommand command) {
        super(app, uiCallback);
        this.caller = caller;
        this.command = command;
        this.link = link;
    }

    @Override
    protected MyOrderRepresentation doInBackground(Void... params) {
        try {
            String body = objectMapper().writeValueAsString(command);
            String onlineTxn = HttpRequest.put(link).send(body).body();
            return objectMapper().readValue(onlineTxn, MyOrderRepresentation.class);
        } catch (Exception e) {
            Log.e("CancelMyOrderTask", e.getMessage(), e);
            return null;
        }
    }
}
