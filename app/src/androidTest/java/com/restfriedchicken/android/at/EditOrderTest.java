package com.restfriedchicken.android.at;

import com.restfriedchicken.android.MainActivity;
import com.restfriedchicken.android.orders.DisplayMyOrderActivity;


public class EditOrderTest extends
        RobotiumAcceptanceTestCase<MainActivity> {

    private OrderFixture orderFixture;

    public EditOrderTest() {
        super(MainActivity.class);
    }

    @Override
    public void doSetUp() {
        orderFixture = new OrderFixture(getSolo());
    }

    public void test_order_is_SERVING_after_making_payment() throws Exception {

        orderFixture.navigateToMyOrders();

        String trackingId = "for_update_test";

        orderFixture.viewMyOrder(trackingId);

        assertTrue("It should suggest amount with[21.0].", orderFixture.getSolo().searchText("21.0"));

        orderFixture.startEdition();

        orderFixture.editAmount("22.0");

        orderFixture.submitEdition();

        assertTrue("It should suggest amount with[22.0].", orderFixture.getSolo().searchText("22.0"));
        assertTrue("It should suggest button[Edit].", orderFixture.getSolo().searchButton("Edit", true));
    }


    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }

}