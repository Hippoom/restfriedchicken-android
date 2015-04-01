package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;

import com.robotium.solo.Solo;


public class MyOrdersTest extends
        RobotiumAcceptanceTestCase<MainActivity> {

    private OrderFixture orderFixture;

    public MyOrdersTest() {
        super(MainActivity.class);
    }

    @Override
    public void doSetUp() {
        orderFixture = new OrderFixture(getSolo());
    }

    public void testMyOrdersAreShown() throws Exception {

        when_I_navigate_to_my_orders();

        then_it_should_display_my_orders_which_status_is_WAIT_PAYMENT();

        when_I_swipe_to_view_the_orders_that_are_being_SERVING();

        then_it_should_display_my_orders_which_status_is_SERVING();
    }

    private void when_I_swipe_to_view_the_orders_that_are_being_SERVING() {
        orderFixture.getSolo().scrollToSide(Solo.RIGHT);
        orderFixture.getSolo().sleep(2);
    }

    private void then_it_should_display_my_orders_which_status_is_SERVING() {

        assertTrue(getSolo().searchText("tracking_id_2", true));
        assertTrue(getSolo().searchText("tracking_id_3", true));
    }

    private void when_I_navigate_to_my_orders() {
        orderFixture.navigateToMyOrders();
    }

    private void then_it_should_display_my_orders_which_status_is_WAIT_PAYMENT() {
        assertTrue(getSolo().searchText("tracking_id_1", true));
    }

    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }
}