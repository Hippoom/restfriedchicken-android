package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.Link;
import com.restfriedchicken.rest.orders.EditOrderCommand;
import com.restfriedchicken.rest.orders.OrderRepresentation;
import com.restfriedchicken.rest.orders.OrderResource;

public class DisplayMyOrderActivity extends Activity {

    private String selfHref;


    private TextView trackingIdView;
    private TextView amountView;
    private TextView statusView;

    private EditText amountEdit;

    private Button editButton;
    private Button payButton;
    private Button cancelButton;
    private OrderResource orderResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        this.selfHref = intent.getStringExtra("self_link_href");

        initLayout();

        this.orderResource = ((RestfriedChickenApp) getApplication()).provideOrderResource();
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
        new GetMyOrderTask(((RestfriedChickenApp) getApplication()).provideOrderResource(), new Link("self", selfHref), new MyOrderUiRenderer(this)).execute();
    }

    public void setTextForStatusView(String status) {
        this.statusView.setText(status);
    }

    public OrderResource getOrderResource() {
        return orderResource;
    }

    static class MyOrderUiRenderer extends UiCallback<OrderRepresentation> {

        private DisplayMyOrderActivity caller;

        MyOrderUiRenderer(DisplayMyOrderActivity caller) {
            this.caller = caller;
        }

        @Override
        public void handle(OrderRepresentation order) {

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
        private OrderRepresentation order;


        EditButtonClickListener(DisplayMyOrderActivity caller, OrderRepresentation order) {
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
        private OrderRepresentation order;


        CancelButtonClickListener(DisplayMyOrderActivity caller, OrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            new CancelMyOrderTask(caller.getOrderResource(), order.getCancelLink(), new MyOrderUiRenderer(caller)).execute();
        }
    }

    static class EditButtonClickSubmitListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private OrderRepresentation order;


        EditButtonClickSubmitListener(DisplayMyOrderActivity caller, OrderRepresentation order) {
            this.caller = caller;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            new EditOrderTask(caller.getOrderResource(), order.getLink("edit"), new EditOrderCommand(caller.getAmountEditText()), new MyOrderUiRenderer(caller)).execute();
        }
    }

    private String getAmountEditText() {
        return amountEdit.getText().toString();
    }

    private void renderEditForm(OrderRepresentation order) {
        hide(amountView);
        show(amountEdit);
        amountEdit.setText(amountView.getText().toString());


        editButton.setText(R.string.button_submit);
        editButton.setOnClickListener(new EditButtonClickSubmitListener(this, order));
    }

    static class MakePaymentButtonClickListener implements View.OnClickListener {
        private DisplayMyOrderActivity caller;
        private OrderRepresentation order;

        MakePaymentButtonClickListener(DisplayMyOrderActivity caller, OrderRepresentation order) {
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


}
