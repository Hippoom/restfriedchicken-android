package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;


public class WelcomeTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public WelcomeTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testWelcomeIsShown() throws Exception {
        assertTrue(solo.searchText("Welcome!"));
    }

    public void testMyOrdersLinkIsShown() throws Exception {
        assertTrue(solo.searchText("My Orders"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}