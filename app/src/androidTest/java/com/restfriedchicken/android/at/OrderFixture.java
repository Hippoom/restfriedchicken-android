package com.restfriedchicken.android.at;

import com.robotium.solo.Solo;

public class OrderFixture {
    private Solo solo;

    public OrderFixture(Solo solo) {
        this.solo = solo;
    }

    public void redirectToMyOrders() {
        getSolo().clickOnButton("My Orders");
        getSolo().sleep(2);// wait for interaction with backend
    }

    public Solo getSolo() {
        return solo;
    }
}
