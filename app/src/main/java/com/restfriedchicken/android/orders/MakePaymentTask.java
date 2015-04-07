package com.restfriedchicken.android.orders;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.onlinetxn.MakeOnlineTxnCommand;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnRepresentation;

public class MakePaymentTask extends GetCustomerResourceTask<OnlineTxnRepresentation> {
    private MakePaymentActivity caller;

    MakePaymentTask(RestfriedChickenApp app, UiCallback<OnlineTxnRepresentation> uiCallback, MakePaymentActivity caller) {
        super(app, uiCallback);
        this.caller = caller;
    }

    @Override
    protected OnlineTxnRepresentation doInBackground(Void... params) {
        try {

            MakeOnlineTxnCommand command = new MakeOnlineTxnCommand(caller.getAmount(), caller.getCreditCardNumber(), caller.getCreditCardExpireDate(), caller.getCreditCardCVV2());
            String body = objectMapper().writeValueAsString(command);
            String onlineTxn = HttpRequest.post(caller.getLink()).send(body).body();
            return objectMapper().readValue(onlineTxn, OnlineTxnRepresentation.class);
        } catch (Exception e) {
            Log.e("CancelMyOrderTask", e.getMessage(), e);
            return null;
        }
    }
}
