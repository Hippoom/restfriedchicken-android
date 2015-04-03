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
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.onlinetxn.MakeOnlineTxnCommand;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnRepresentation;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

public class DisplayMyOrderActivity extends Activity {

    private String selfHref;
    private MyOrderRepresentation order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_order);

        Intent intent = getIntent();

        this.selfHref = intent.getStringExtra("self_link_href");
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

    protected MyOrderRepresentation getOrder() {
        return order;
    }

    protected void setOrder(MyOrderRepresentation order) {
        this.order = order;
    }

    static class MyOrderUiRenderer extends GetCustomerResourceTask.UiCallback<MyOrderRepresentation> {

        private DisplayMyOrderActivity caller;

        MyOrderUiRenderer(DisplayMyOrderActivity caller) {
            this.caller = caller;
        }

        @Override
        public void handle(MyOrderRepresentation order) {
            caller.setOrder(order);

            TextView trackingId = (TextView) caller.findViewById(R.id.my_order_tracking_id);
            TextView amount = (TextView) caller.findViewById(R.id.my_order_amount);
            amount.setVisibility(View.VISIBLE);
            TextView status = (TextView) caller.findViewById(R.id.my_order_status);

            trackingId.setText(order.getTrackingId());
            amount.setText(order.getAmount());
            status.setText(order.getStatus());

            EditText amountEdit = (EditText) caller.findViewById(R.id.my_order_amount_edit);
            amountEdit.setVisibility(View.INVISIBLE);


            Button edit = (Button) caller.findViewById(R.id.button_edit);
            edit.setVisibility(View.INVISIBLE);
            edit.setText(R.string.button_edit);
            Button makePayment = (Button) caller.findViewById(R.id.button_make_payment);
            makePayment.setVisibility(View.INVISIBLE);
            Button cancel = (Button) caller.findViewById(R.id.button_cancel);
            cancel.setVisibility(View.INVISIBLE);

            if (order.isAvalableToEdit()) {
                edit.setVisibility(View.VISIBLE);
                edit.setOnClickListener(new EditButtonClickListener(caller, order));
            }
            if (order.isAvailableToMakePayment()) {
                makePayment.setVisibility(View.VISIBLE);
                makePayment.setOnClickListener(new MakePaymentButtonClickListener(caller, order));
            }
            if (order.isAvailableToCancel()) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CancelMyOrderTask((RestfriedChickenApp) caller.getApplication(), new MyOrderUiRenderer(caller), caller.getOrder().getCancelLink()).execute();
                    }
                });
            }

        }

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
            this.caller.renderForm();
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
            EditText amountEdit = (EditText) caller.findViewById(R.id.my_order_amount_edit);
            new EditOrderTask((RestfriedChickenApp) caller.getApplication(), new MyOrderUiRenderer(caller), caller, order.getLink("edit").getHref(), new EditOrderCommand(amountEdit.getText().toString())).execute();
        }
    }

    private void renderForm() {
        TextView amount = (TextView) findViewById(R.id.my_order_amount);
        amount.setVisibility(View.INVISIBLE);
        EditText amountEdit = (EditText) findViewById(R.id.my_order_amount_edit);
        amountEdit.setVisibility(View.VISIBLE);
        amountEdit.setText(amount.getText().toString());

        Button edit = (Button) findViewById(R.id.button_edit);
        edit.setText(R.string.button_submit);
        edit.setOnClickListener(new EditButtonClickSubmitListener(this, order));
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
