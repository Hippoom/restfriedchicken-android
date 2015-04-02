package com.restfriedchicken.android.at;

import com.restfriedchicken.android.MainActivity;
import com.restfriedchicken.android.orders.DisplayMyOrderActivity;


public class MakePaymentTest extends
        RobotiumAcceptanceTestCase<MainActivity> {

    private OrderFixture orderFixture;

    public MakePaymentTest() {
        super(MainActivity.class);
    }

    @Override
    public void doSetUp() {
        orderFixture = new OrderFixture(getSolo());
    }

    public void test_order_is_SERVING_after_making_payment() throws Exception {

        orderFixture.navigateToMyOrders();

        String trackingId = "for_make_payment_test";

        orderFixture.viewMyOrder(trackingId);

        orderFixture.navigateToMakePaymentForm();

        assertTrue("It should suggest amount with[21.0].", orderFixture.getSolo().searchText("21.0"));

        orderFixture.makePayment();

        assertTrue("It should tell transaction is submitted", orderFixture.getSolo().waitForText("Transaction is submitted", 1, 2));

        orderFixture.getSolo().assertCurrentActivity("It should get back to detail order screen", DisplayMyOrderActivity.class);
    }


    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }

}