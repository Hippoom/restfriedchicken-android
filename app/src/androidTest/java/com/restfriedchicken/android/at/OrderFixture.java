package com.restfriedchicken.android.at;

import android.widget.EditText;
import android.widget.TextView;

import com.restfriedchicken.android.R;
import com.robotium.solo.Solo;

public class OrderFixture {
    private Solo solo;
    private String trackingId;
    private String amount;

    public OrderFixture(Solo solo) {
        this.solo = solo;
    }

    public void navigateToMyOrders() {
        getSolo().clickOnMenuItem("My Orders");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public void viewMyOrder(String trackingId) {
        getSolo().clickOnText(trackingId);
        getSolo().sleep(2);// wait for interaction with backend

        TextView trackingIdView = (TextView) getSolo().getView(R.id.my_order_tracking_id);
        TextView amountView = (TextView) getSolo().getView(R.id.my_order_amount);
        this.trackingId = String.valueOf(trackingIdView.getText());
        this.amount = String.valueOf(amountView.getText());
    }

    public void cancel() {
        getSolo().clickOnButton("Cancel");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public Solo getSolo() {
        return solo;
    }

    public void navigateToMakePaymentForm() {
        getSolo().clickOnButton("Pay");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public void makePayment() {
        EditText creditCardNumEdit = (EditText) getSolo().getView(R.id.credit_card_number_edit);
        EditText expireDateEdit = (EditText) getSolo().getView(R.id.expire_date_edit);
        EditText cvv2Edit = (EditText) getSolo().getView(R.id.cvv2_edit);

        getSolo().clearEditText(creditCardNumEdit);
        getSolo().enterText(creditCardNumEdit, "456789");
        getSolo().clearEditText(expireDateEdit);
        getSolo().enterText(expireDateEdit, "10/16");
        getSolo().clearEditText(cvv2Edit);
        getSolo().enterText(cvv2Edit, "233");

        getSolo().clickOnButton("Pay");
        getSolo().sleep(2);// wait for interaction with backend
    }
}
