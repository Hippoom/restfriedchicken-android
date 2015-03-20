package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.restfriedchicken.android.R;

public class MyOrdersAdapter extends ArrayAdapter<MyOrderRepresentation> {

    public MyOrdersAdapter(Context context, int resource, MyOrderRepresentation[] orders) {
        super(context, resource, orders);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyOrderRepresentation order = getItem(position);

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.my_orders_row, parent, false);
            MyOrderViewHolder h = new MyOrderViewHolder();
            h.trackingId = (TextView) rowView.findViewById(R.id.my_orders_row_tracking_id);
            h.status = (TextView) rowView.findViewById(R.id.my_orders_row_status);
            rowView.setTag(h);
        }

        MyOrderViewHolder h = (MyOrderViewHolder) rowView.getTag();

        h.trackingId.setText(order.getTrackingId());
        h.status.setText(order.getStatus());
        h.self = order.getLink("self");

        rowView.setOnClickListener(new OnMyOrderClickListener(getContext(), h));

        return rowView;
    }

    static class OnMyOrderClickListener implements View.OnClickListener {
        private Context context;
        private MyOrderViewHolder viewHolder;

        OnMyOrderClickListener(Context context, MyOrderViewHolder viewHolder) {
            this.context = context;
            this.viewHolder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DisplayMyOrderActivity.class);
            intent.putExtra("self_link_href", viewHolder.self.getHref());
            context.startActivity(intent);
        }
    }
}
