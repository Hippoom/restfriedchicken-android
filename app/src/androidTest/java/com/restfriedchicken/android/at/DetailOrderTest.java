package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;


public class DetailOrderTest extends
        RobotiumAcceptanceTestCase<MainActivity> {

    private OrderFixture orderFixture;

    public DetailOrderTest() {
        super(MainActivity.class);
    }

    @Override
    public void doSetUp() {
        orderFixture = new OrderFixture(getSolo());
    }

    public void test_order_details_should_be_displayed() throws Exception {

        orderFixture.navigateToMyOrders();

        String trackingId = "tracking_id_1";

        orderFixture.viewMyOrder(trackingId);

        assertTrue("It should show tracking id with[" + trackingId + "].", orderFixture.getSolo().searchText(trackingId));
        assertTrue("It should show status with[WAIT_PAYMENT].", orderFixture.getSolo().searchText("WAIT_PAYMENT"));
        assertTrue("It should be available to make a payment.", orderFixture.getSolo().searchButton("Pay", true));
        assertTrue("It should be available to cancel.", orderFixture.getSolo().searchButton("Cancel", true));
    }


    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }
}