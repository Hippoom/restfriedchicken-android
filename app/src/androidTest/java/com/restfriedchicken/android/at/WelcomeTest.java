package com.restfriedchicken.android.at;

import android.test.ActivityInstrumentationTestCase2;

import com.restfriedchicken.android.MainActivity;
import com.robotium.solo.Solo;


public class WelcomeTest extends
        RobotiumAcceptanceTestCase<MainActivity> {


    public WelcomeTest() {
        super(MainActivity.class);
    }

    public void testWelcomeIsShown() throws Exception {
        assertTrue(getSolo().searchText("Welcome!"));
    }

    @Override
    public void tearDown() throws Exception {
        getSolo().finishOpenedActivities();
    }
}