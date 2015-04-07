package com.restfriedchicken.android.orders;

import android.os.AsyncTask;
import android.util.Log;

import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.onlinetxn.MakeOnlineTxnCommand;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnRepresentation;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnResource;

public class MakePaymentTask extends AsyncTask<Void, Void, OnlineTxnRepresentation> {
    private OnlineTxnResource onlineTxnResource;
    private Link link;
    private MakeOnlineTxnCommand command;
    private UiCallback<OnlineTxnRepresentation> uiCallback;

    public MakePaymentTask(OnlineTxnResource onlineTxnResource, Link link, MakeOnlineTxnCommand command, UiCallback<OnlineTxnRepresentation> uiCallback) {
        this.onlineTxnResource = onlineTxnResource;
        this.link = link;
        this.command = command;
        this.uiCallback = uiCallback;
    }

    @Override
    protected OnlineTxnRepresentation doInBackground(Void... params) {
        try {

            return onlineTxnResource.create(link, command);
        } catch (Exception e) {
            Log.e("CancelMyOrderTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(OnlineTxnRepresentation result) {
        this.uiCallback.handle(result);
    }
}
