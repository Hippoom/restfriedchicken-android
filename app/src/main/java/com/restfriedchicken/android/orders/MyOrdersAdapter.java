package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.restfriedchicken.android.R;

import java.util.HashMap;
import java.util.Map;

public class MyOrdersAdapter extends ArrayAdapter<MyOrderRepresentation> {
    private ListView myOrdersView;

    public MyOrdersAdapter(Context context, int resource, MyOrderRepresentation[] orders, ListView listView) {
        super(context, resource, orders);
        this.myOrdersView = listView;
    }


    static class ViewHolder {
        TextView trackingId;
        Button btn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyOrderRepresentation order = getItem(position);

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.my_orders_row, parent, false);
            ViewHolder h = new ViewHolder();
            h.trackingId = (TextView) rowView.findViewById(R.id.my_orders_row_tracking_id);
            h.btn = (Button) rowView.findViewById(R.id.my_orders_row_button);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.trackingId.setText(order.getTrackingId());

        Map<String, String> tag = new HashMap<>();
        tag.put("id", "button_" + order.getTrackingId());
        h.btn.setTag(tag);
        if (order.isPayable()) {
            h.btn.setVisibility(View.VISIBLE);
        } else {
            h.btn.setVisibility(View.INVISIBLE);
        }
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO what you want to recieve on btn click there.
            }
        });
        return rowView;
    }
}
