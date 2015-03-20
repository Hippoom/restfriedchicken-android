package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;

import java.util.HashMap;
import java.util.Map;


public class DetailOrderTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private OrderFixture orderFixture;

    public DetailOrderTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        orderFixture = new OrderFixture(new Solo(getInstrumentation(), getActivity()));
    }

    public void testPaymentMakingShouldBeSuggested() throws Exception {

        redirectToMyOrders();

        String trackingId = selectOrderWhichStatusIsWaitPayment();

        assertPaymentMakeingShouldBeSuggestedFor(trackingId);
    }

    private void assertPaymentMakeingShouldBeSuggestedFor(String trackingId) {
        orderFixture.getSolo().sleep(2);

        assertTrue("It does not suggest to make a payment.", orderFixture.getSolo().searchButton("Pay"));
    }

    private String selectOrderWhichStatusIsWaitPayment() {
        String tracking_id_1 = "tracking_id_1";

        orderFixture.getSolo().clickOnText(tracking_id_1);

        return tracking_id_1;
    }

    private void redirectToMyOrders() {
        orderFixture.redirectToMyOrders();
    }


    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }

    private Solo getSolo() {
        return orderFixture.getSolo();
    }
}