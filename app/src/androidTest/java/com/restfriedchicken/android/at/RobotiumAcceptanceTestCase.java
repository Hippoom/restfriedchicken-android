package com.restfriedchicken.android.at;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

public abstract class RobotiumAcceptanceTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    private Solo solo;

    public RobotiumAcceptanceTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() {
        this.solo = new Solo(getInstrumentation(), getActivity());
        doSetUp();
    }

    protected void doSetUp() {

    }

    public Solo getSolo() {
        return solo;
    }

    @Override
    public void runTest() throws Throwable {
        try{
            super.runTest();
        }
        catch (Throwable t) {
            String testCaseName = String.format("%s.%s", getClass().getName(), getName());
            solo.takeScreenshot(testCaseName);
            Log.e("Boom! Screenshot!", String.format("Captured screenshot for failed test: %s", testCaseName));
            throw t;
        }
    }
}
