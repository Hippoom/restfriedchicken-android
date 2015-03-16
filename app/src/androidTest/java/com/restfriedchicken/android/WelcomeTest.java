package com.restfriedchicken.android;

import android.test.ActivityInstrumentationTestCase2;

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

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}