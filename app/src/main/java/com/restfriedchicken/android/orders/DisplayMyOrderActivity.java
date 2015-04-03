package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

import java.util.Arrays;
import java.util.List;

public class DisplayMyOrderActivity extends Activity {

    private String selfHref;


    private TextView trackingIdView;
    private TextView amountView;
    private TextView statusView;

    private EditText amountEdit;

    private Button editButton;
    private Button payButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        this.selfHref = intent.getStringExtra("self_link_href");

        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_display_my_order);
        this.trackingIdView = (TextView) findViewById(R.id.my_order_tracking_id);
        this.amountView = (TextView) findViewById(R.id.my_order_amount);
        this.statusView = (TextView) findViewById(R.id.my_order_status);


        this.amountEdit = (EditText) findViewById(R.id.my_order_amount_edit);

        this.editButton = (Button) findViewById(R.id.button_edit);
        this.payButton = (Button) findViewById(R.id.button_make_payment);
        this.cancelButton = (Button) findViewById(R.id.button_cancel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.button_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetMyOrderTask((RestfriedChickenApp) getApplication(), new MyOrderUiRenderer(this), selfHref).execute();
    }

    public void setTextForStatusView(String status) {
        this.statusView.setText(status);
    }

    static class MyOrderUiRenderer extends GetCustomerResourceTask.UiCallback<MyOrderRepresentation> {

        private DisplayMyOrderActivity caller;

        MyOrderUiRenderer(DisplayMyOrderActivity caller) {
            this.caller = caller;
        }

        @Override
        public void handle(MyOrderRepresentation order) {

            caller.initLayout();

            caller.setTextForTrackingIdView(order.getTrackingId());
            caller.setTextForAmountView(order.getAmount());
            caller.setTextForStatusView(order.getStatus());

            if (order.isAvalableToEdit()) {
                caller.showEditButton();
                caller.setOnClickListenerForEditButton(new EditButtonClickListener(caller, order));
            }
            if (order.isAvailableToMakePayment()) {
                caller.showPayButton();
                caller.setOnClickListenerForPayButton(new MakePaymentButtonClickListener(caller, order));
            }
            if (order.isAvailableToCancel()) {
                caller.showCancelButton();
                caller.setOnClickListenerForCancelButton(new CancelButtonClickListener(caller, order));
            }

        }

    }

    private void setOnClickListenerForCancelButton(View.OnClickListener listener) {
        setOnClickListener(cancelButton, listener);
    }

    private void showCancelButton() {
        show(cancelButton);
    }

    private void setOnClickListenerForPayButton(View.OnClickListener listener) {
        setOnClickListener(payButton, listener);
    }

    private void setOnClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    private void showPayButton() {
        show(payButton);
    }

    private void setOnClickListenerForEditButton(View.OnClickListener listener) {
        setOnClickListener(editButton, listener);
    }

    private void showEditButton() {
        show(editButton);
    }

    private void hide(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setTextForAmountView(String amount) {
        this.amountView.setText(amount);
    }

    private void setTextForTrackingIdView(String trackingId) {
        this.trackingIdView.setText(trackingId);

    }

    static class EditButtonClickListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private MyOrderRepresentation order;


        EditButtonClickListener(DisplayMyOrderActivity caller, MyOrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            this.caller.renderEditForm(order);
        }
    }

    static class CancelButtonClickListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private MyOrderRepresentation order;


        CancelButtonClickListener(DisplayMyOrderActivity caller, MyOrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            new CancelMyOrderTask((RestfriedChickenApp) caller.getApplication(), new MyOrderUiRenderer(caller), order.getCancelLink()).execute();
        }
    }

    static class EditButtonClickSubmitListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private MyOrderRepresentation order;


        EditButtonClickSubmitListener(DisplayMyOrderActivity caller, MyOrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            new EditOrderTask((RestfriedChickenApp) caller.getApplication(), new MyOrderUiRenderer(caller), caller, order.getLink("edit").getHref(), new EditOrderCommand(caller.getAmountEditText())).execute();
        }
    }

    private String getAmountEditText() {
        return amountEdit.getText().toString();
    }

    private void renderEditForm(MyOrderRepresentation order) {
        hide(amountView);
        show(amountEdit);
        amountEdit.setText(amountView.getText().toString());


        editButton.setText(R.string.button_submit);
        editButton.setOnClickListener(new EditButtonClickSubmitListener(this, order));
    }

    static class MakePaymentButtonClickListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private MyOrderRepresentation order;

        MakePaymentButtonClickListener(DisplayMyOrderActivity caller, MyOrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(caller, MakePaymentActivity.class);
            intent.putExtra("amount_due", order.getAmount());
            intent.putExtra("make_payment_link", order.getLink("payment").getHref());
            caller.startActivityForResult(intent, MakePaymentActivity.REQUEST_CODE_MAKE_PAYMENT);
        }
    }

    static class CancelMyOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {
        private Link link;

        CancelMyOrderTask(RestfriedChickenApp app, UiCallback<MyOrderRepresentation> uiCallback, Link link) {
            super(app, uiCallback);
            this.link = link;
        }

        @Override
        protected MyOrderRepresentation doInBackground(Void... params) {
            try {
                String order = HttpRequest.delete(link.getHref()).body();

                return objectMapper().readValue(order, MyOrderRepresentation.class);
            } catch (Exception e) {
                Log.e("CancelMyOrderTask", e.getMessage(), e);
                return null;
            }
        }

    }

    static class EditOrderTask extends GetCustomerResourceTask<MyOrderRepresentation> {
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


}
