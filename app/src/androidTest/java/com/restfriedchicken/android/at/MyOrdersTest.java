package com.restfriedchicken.android.at;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;

import com.robotium.solo.Solo;


public class MyOrdersTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private OrderFixture orderFixture;

    public MyOrdersTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        orderFixture = new OrderFixture(new Solo(getInstrumentation(), getActivity()));
    }

    public void testMyOrdersAreShown() throws Exception {

        redirectToMyOrders();

        assertMyOrdersAreShown();
    }

    private void redirectToMyOrders() {
        orderFixture.redirectToMyOrders();
    }

    private void assertMyOrdersAreShown() {
        assertTrue(getSolo().searchText("tracking_id_1"));
        assertTrue(getSolo().searchText("tracking_id_2"));
        assertTrue(getSolo().searchText("tracking_id_3"));
    }

    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }

    private Solo getSolo() {
        return orderFixture.getSolo();
    }
}