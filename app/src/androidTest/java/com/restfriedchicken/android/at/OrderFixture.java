package com.restfriedchicken.android.at;

import com.robotium.solo.Solo;

public class OrderFixture {
    private Solo solo;

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
    }

    public void cancel() {
        getSolo().clickOnButton("Cancel");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public Solo getSolo() {
        return solo;
    }
}
