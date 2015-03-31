package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;

public class DisplayMyOrdersActivity extends Activity {
    private ListView myOrdersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_orders);
        myOrdersView = (ListView) findViewById(android.R.id.list);
        myOrdersView.setEmptyView(findViewById(android.R.id.empty));
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
        if (id == R.id.button_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetMyOrdersTask((RestfriedChickenApp) getApplication()
                , new MyOrdersUiRenderer(this, myOrdersView)).execute();
    }

    static class MyOrdersUiRenderer extends GetMyOrdersTask.UiCallback<MyOrdersRepresentation> {

        private DisplayMyOrdersActivity caller;
        private ListView myOrdersView;

        MyOrdersUiRenderer(DisplayMyOrdersActivity caller, ListView myOrdersView) {
            this.caller = caller;
            this.myOrdersView = myOrdersView;
        }

        @Override
        public void handle(MyOrdersRepresentation orders) {
            MyOrderRepresentation[] orderArray = new MyOrderRepresentation[orders.getOrders().size()];

            for (int i = 0; i < orders.getOrders().size(); i++) {
                orderArray[i] = orders.getOrders().get(i);
            }

            myOrdersView.setAdapter(new MyOrdersAdapter(caller,
                    android.R.layout.simple_list_item_1, orderArray));
        }
    }

}
