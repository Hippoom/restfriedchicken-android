package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
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
            TextView status = (TextView) caller.findViewById(R.id.my_order_status);

            trackingId.setText(order.getTrackingId());
            amount.setText(order.getAmount());
            status.setText(order.getStatus());



            Button makePayment = (Button) caller.findViewById(R.id.button_make_payment);
            makePayment.setVisibility(View.INVISIBLE);
            Button cancel = (Button) caller.findViewById(R.id.button_cancel);
            cancel.setVisibility(View.INVISIBLE);

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
}
