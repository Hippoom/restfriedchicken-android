package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;


public class DetailOrderTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private OrderFixture orderFixture;

    public DetailOrderTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        orderFixture = new OrderFixture(new Solo(getInstrumentation(), getActivity()));
    }

    public void test_order_details_should_be_displayed() throws Exception {

        redirectToMyOrders();

        String trackingId = selectOrderWhichStatusIsWaitPayment();

        orderFixture.getSolo().sleep(2);

        assertTrue("Cannot find tracking id.", orderFixture.getSolo().searchText(trackingId));
        assertTrue("Cannot find status.", orderFixture.getSolo().searchText("WAIT_PAYMENT"));


        assertPaymentMakeingShouldBeSuggestedFor(trackingId);
        assertCancellationShouldBeSuggestedFor(trackingId);
    }

    private void assertCancellationShouldBeSuggestedFor(String trackingId) {
        assertTrue("It does not suggest to cancel.", orderFixture.getSolo().searchButton("Cancel", true));
    }

    private void assertPaymentMakeingShouldBeSuggestedFor(String trackingId) {
        assertTrue("It does not suggest to make a payment.", orderFixture.getSolo().searchButton("Pay", true));
    }

    private String selectOrderWhichStatusIsWaitPayment() {
        String tracking_id_1 = "tracking_id_1";

        orderFixture.getSolo().clickOnText(tracking_id_1);

        return tracking_id_1;
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