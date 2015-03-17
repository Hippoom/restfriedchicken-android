package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class DisplayMyOrdersActivity extends Activity {
    private ListView myOrdersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_orders);
        myOrdersView = (ListView) findViewById(android.R.id.list);
        myOrdersView.setEmptyView(findViewById(android.R.id.empty));
    }

    private String[] loadMyOrders() {
        return new String[]{"tracking_id_1", "tracking_id_2", "tracking_id_3"};
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask(this).execute();
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, MyOrdersRepresentation> {

        private Context context;

        private HttpRequestTask(Context context) {
            this.context = context;
        }

        @Override
        protected MyOrdersRepresentation doInBackground(Void... params) {
            Log.i("DisplayMyOrdersActivity", "Begin to load orders for the customer");
            try {
                final String url = "http://192.168.80.145:12306/customer/1/orders";
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setObjectMapper(objectMapper);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(converter);
                MyOrdersRepresentation orders = restTemplate.getForObject(url, MyOrdersRepresentation.class);
                return orders;
            } catch (Exception e) {
                Log.e("DisplayMyOrdersActivity", e.getMessage(), e);
            }
            return new MyOrdersRepresentation();
        }

        @Override
        protected void onPostExecute(MyOrdersRepresentation orders) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, orders.getOrders());

            myOrdersView.setAdapter(adapter);
        }

    }
}
