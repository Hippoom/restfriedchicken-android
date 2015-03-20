package com.restfriedchicken.android.orders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.restfriedchicken.android.R;

public class DisplayMyOrdersGroupedByStatusActivity extends FragmentActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private StatusCategorizedMyOrdersPagerAdapter myOrdersPagerAdapter;
    private ViewPager myOrdersPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_orders_grouped_by_status);

        myOrdersPagerAdapter =
                new StatusCategorizedMyOrdersPagerAdapter(
                        getSupportFragmentManager());
        myOrdersPager = (ViewPager) findViewById(R.id.pager);
        myOrdersPager.setAdapter(myOrdersPagerAdapter);
    }

    static class StatusCategorizedMyOrdersPagerAdapter extends FragmentPagerAdapter {
        public StatusCategorizedMyOrdersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment status = new OrderStatusFragment();
            Bundle args = new Bundle();

            switch (i) {
                case 0:
                    args.putString("status", "WAIT_PAYMENT");
                    break;
                case 1:
                    args.putString("status", "SERVING");
                    break;
                case 2:
                    args.putString("status", "SERVED");
                    break;
                case 3:
                    args.putString("status", "CANCELED");
                    break;
                default:
                    args.putString("status", "OTHERS");
            }
            status.setArguments(args);
            return status;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "WAIT_PAYMENT";
                case 1:
                    return "SERVING";
                case 2:
                    return "SERVED";
                case 3:
                    return "CANCELED";
                default:
                    return "OTHERS";
            }
        }
    }


    public static class OrderStatusFragment extends Fragment {
        private ListView myOrdersView;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.activity_display_my_orders, container, false);

            myOrdersView = (ListView) rootView.findViewById(android.R.id.list);
            myOrdersView.setEmptyView(rootView.findViewById(android.R.id.empty));

            String statusEq = getArguments().getString("status");

            new GetMyOrdersTask((com.restfriedchicken.android.RestfriedChickenApp) getActivity().getApplication(), new MyOrdersUiRenderer(getActivity(), myOrdersView), statusEq).execute();

            return rootView;
        }
    }

    static class MyOrdersUiRenderer extends GetMyOrdersTask.UiCallback {

        private Context caller;
        private ListView myOrdersView;

        MyOrdersUiRenderer(Context caller, ListView myOrdersView) {
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
