package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;


public class CancelOrderTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private OrderFixture orderFixture;

    public CancelOrderTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        orderFixture = new OrderFixture(new Solo(getInstrumentation(), getActivity()));
    }

    public void test_order_details_should_be_displayed() throws Exception {

        redirectToMyOrders();

        String trackingId1 = "for_cancel_test";

        orderFixture.viewMyOrder(trackingId1);

        assertTrue("It should show status with[WAIT_PAYMENT].", orderFixture.getSolo().searchText("WAIT_PAYMENT"));

        orderFixture.cancel();

        assertTrue("It should show status with[CANCELED].", orderFixture.getSolo().searchText("CANCELED"));

        assertFalse(orderFixture.getSolo().searchButton("Pay", true));
        assertFalse(orderFixture.getSolo().searchButton("Cancel", true));
    }

    private void redirectToMyOrders() {
        orderFixture.navigateToMyOrders();
    }


    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }

    private Solo getSolo() {
        return orderFixture.getSolo();
    }
}