package com.restfriedchicken.android.at;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;


public class MyOrdersTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MyOrdersTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testMyOrdersAreShown() throws Exception {

        solo.clickOnButton("My Orders");

        solo.sleep(2);// wait for interaction with backend

        assertMyOrdersAreShown();
    }

    private void assertMyOrdersAreShown() {
        assertTrue(solo.searchText("tracking_id_1"));
        assertTrue(solo.searchText("tracking_id_2"));
        assertTrue(solo.searchText("tracking_id_3"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}