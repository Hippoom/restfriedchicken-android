package com.restfriedchicken.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.restfriedchicken.android.orders.DisplayMyOrdersGroupedByStatusActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.button_my_orders:
                navigateToMyOrders();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the My Orders button
     */
    private void navigateToMyOrders() {
        Intent intent = new Intent(this, DisplayMyOrdersGroupedByStatusActivity.class);
        startActivity(intent);
    }
}
