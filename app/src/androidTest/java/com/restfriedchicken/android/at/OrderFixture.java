package com.restfriedchicken.android.at;

import com.robotium.solo.Solo;

public class OrderFixture {
    private Solo solo;

    public OrderFixture(Solo solo) {
        this.solo = solo;
    }

    public void navigateToMyOrders() {
        getSolo().clickOnText("My Orders");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public void viewMyOrderWith(String trackingId) {
        getSolo().clickOnText(trackingId);
    }

    public Solo getSolo() {
        return solo;
    }
}
