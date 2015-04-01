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

        String trackingId = selectOrderWhichStatusIsWaitPayment();

        orderFixture.getSolo().sleep(2);

        assertTrue(orderFixture.getSolo().searchText("WAIT_PAYMENT"));

        orderFixture.getSolo().clickOnButton("Cancel");

        orderFixture.getSolo().sleep(2);

        assertTrue(orderFixture.getSolo().searchText("CANCELED"));
    }

    private String selectOrderWhichStatusIsWaitPayment() {
        String trackingId = "for_cancel_test";

        orderFixture.getSolo().clickOnText(trackingId);

        return trackingId;
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