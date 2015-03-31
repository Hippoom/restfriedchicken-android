package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.orders.MyOrderRepresentation;

public class DisplayMyOrderActivity extends Activity {

    private String selfHref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_order);

        Intent intent = getIntent();

        this.selfHref = intent.getStringExtra("self_link_href");

        Button makePaymentButton = (Button) findViewById(R.id.button_make_payment);
        makePaymentButton.setVisibility(View.VISIBLE);
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

    static class MyOrderUiRenderer extends GetCustomerResourceTask.UiCallback<MyOrderRepresentation> {

        private DisplayMyOrderActivity caller;

        MyOrderUiRenderer(DisplayMyOrderActivity caller) {
            this.caller = caller;
        }

        @Override
        public void handle(MyOrderRepresentation order) {
            TextView trackingId = (TextView) caller.findViewById(R.id.my_orders_row_tracking_id);
            TextView status = (TextView) caller.findViewById(R.id.my_orders_row_status);

            trackingId.setText(order.getTrackingId());
            status.setText(order.getStatus());

            if (order.isAvailableToMakePayment()) {
                Button makePayment = (Button) caller.findViewById(R.id.button_make_payment);
                makePayment.setVisibility(View.VISIBLE);
            }
            if (order.isAvailableToCancel()) {
                Button cancel = (Button) caller.findViewById(R.id.button_cancel);
                cancel.setVisibility(View.VISIBLE);
            }

        }
    }
}
