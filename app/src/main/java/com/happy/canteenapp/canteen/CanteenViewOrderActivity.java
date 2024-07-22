package com.happy.canteenapp.canteen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.happy.canteenapp.MyOrders;
import com.happy.canteenapp.R;
import com.happy.canteenapp.db.DbHelper;
import com.happy.canteenapp.modals.OrderInfo;
import com.happy.canteenapp.viewholder.CanteenOrderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CanteenViewOrderActivity extends AppCompatActivity
{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
   // private ListView orderListView;
   private RecyclerView orderListView;
    Button home;
    String username;
    //TextView emptyText;


    ArrayList<String> listOrderItems = new ArrayList<String>();
    ArrayList<String> listOrderDateandTime = new ArrayList<String>();
    ArrayList<String> listOrderAmount = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    CanteenOrderAdapter adapter;

    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_view_order);

        getSupportActionBar().setTitle("Canteen Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        home = findViewById(R.id.homeButton);

        dbHelper = new DbHelper(this);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){
            String userEmail = user.getEmail();
            username   = userEmail.substring(0, userEmail.lastIndexOf("@"));
        }

       // DatabaseReference rootRef = database.getReference();
        //dbRef = database.getReference("orders/" + username);
        dbRef = database.getReference("orders");

        ArrayList<OrderInfo> orderArrayList = dbHelper.getOrderData();
        Log.e("Log", "orderArrayList" + orderArrayList);

        orderListView = findViewById(R.id.order_ListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderListView.setLayoutManager(layoutManager);
        orderListView.setItemAnimator(new DefaultItemAnimator());

//        adapter = new CanteenOrderAdapter(CanteenViewOrderActivity.this,android.R.layout.simple_list_item_1, listOrderItems,listOrderDateandTime,listOrderAmount);
//        orderListView.setAdapter(adapter);

        adapter = new CanteenOrderAdapter(this, orderArrayList);
        orderListView.setAdapter(adapter);




       // addChildEventListener();

    }


    private void addChildEventListener() {
        Log.e("Log", "InsideaddChildEventListener");
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /*adapter.add(
                        (String) dataSnapshot.child("itemname").getValue()+" "(String) dataSnapshot.child("price").getValue());*/
                //adapter.add(
                //(String) dataSnapshot.child("itemname").getValue());
                listOrderItems.add((String) dataSnapshot.child("orderItems").getValue());
                Log.e("Log", "listOrderItemsInsideListener" + listOrderItems);
                listOrderDateandTime.add((String) dataSnapshot.child("orderedOn").getValue());
                listOrderAmount.add((String) dataSnapshot.child("amount").getValue());
                adapter.notifyDataSetChanged();


                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listOrderItems.remove(index);
                    listOrderAmount.remove(index);
                    listOrderDateandTime.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

        };
        dbRef.addChildEventListener(childListener);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            default:
                finish();
                break;

            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(),MyOrders.class);
                startActivity(intent);
                //Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.item2:
                signOut();
                Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.item3:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        auth.signOut();
    }

    private class CanteenOrderAdapter extends RecyclerView.Adapter<CanteenOrderViewHolder>
    {
        CanteenViewOrderActivity context;
        ArrayList<OrderInfo> orderArrayList;
        public CanteenOrderAdapter(CanteenViewOrderActivity context, ArrayList<OrderInfo> orderArrayList)
        {
            Log.e("Log", "insideCanteenOrderAdapter" + context);
            Log.e("Log", "orderArrayList" + orderArrayList);
            this.context = context;
            this.orderArrayList = orderArrayList;
        }

        @NonNull
        @Override
        public CanteenOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orderlayout,parent,false);

            return new CanteenOrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CanteenOrderViewHolder holder, int position)
        {
            Log.e("Log", "InsideViewHolder");

            OrderInfo orderInfo = orderArrayList.get(position);
            holder.itemNameTV.setText(orderInfo.getOrderItems());
            holder.dateTimeTV.setText(orderInfo.getOrderedOn());
            holder.totalAmountTV.setText(orderInfo.getAmount());

            Log.e("Log", "itemNameTV" + orderInfo.getOrderItems());

        }

        @Override
        public int getItemCount()
        {
            return orderArrayList.size();
        }
    }

//    private class CanteenOrderAdapter extends ArrayAdapter
//    {
//        ArrayList<String> OrderItemArray,OrderDateandTimeArray,OrderAmountArray;
//        public CanteenOrderAdapter(CanteenViewOrderActivity canteenViewOrderActivity, int simpleListItem1, ArrayList<String> listOrderItems, ArrayList<String> listOrderDateandTime, ArrayList<String> listOrderAmount)
//        {
//            super(canteenViewOrderActivity, R.layout.custom_orderlayout, listOrderItems);
//            this.OrderItemArray = listOrderItems;
//            this.OrderDateandTimeArray = listOrderDateandTime;
//            this.OrderAmountArray = listOrderAmount;
//
//            Log.e("Log","listOrderItemsInsideAdapter" + listOrderItems);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            /*if(convertView==null)
//                emptyText.setVisibility(View.VISIBLE);
//            else
//                emptyText.setVisibility(View.INVISIBLE);*/
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View v = inflater.inflate(R.layout.custom_orderlayout, parent, false);
//            TextView itemname = (TextView) v.findViewById(R.id.itemname);
//            TextView dateandtime = (TextView) v.findViewById(R.id.dateandtime);
//            TextView amount = (TextView) v.findViewById(R.id.totalamount);
//            itemname.setText(OrderItemArray.get(position));
//            dateandtime.setText(OrderDateandTimeArray.get(position));
//            amount.setText(OrderAmountArray.get(position));
//
//            /*itemname.setText(OrderItemArray.get(position));
//            dateandtime.setText(OrderDateandTimeArray.get(position));
//            amount.setText(OrderAmountArray.get(position));*/
//            return v;
//
//        }
//
//    }


//    class CanteenOrderAdapter extends ArrayAdapter {
//        ArrayList<String> OrderItemArray,OrderDateandTimeArray,OrderAmountArray;
//
//        public CanteenOrderAdapter(Context c, int x, ArrayList<String> listOrderItems, ArrayList<String> listOrderDateandTime, ArrayList<String> listOrderAmount) {
//            super(c, R.layout.custom_orderlayout, R.id.itemname,listOrderItems);
//            this.OrderItemArray = listOrderItems;
//            this.OrderDateandTimeArray = listOrderDateandTime;
//            this.OrderAmountArray = listOrderAmount;
//
//            Log.e("Log","listOrderItemsInsideAdapter" + listOrderItems);
//
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            /*if(convertView==null)
//                emptyText.setVisibility(View.VISIBLE);
//            else
//                emptyText.setVisibility(View.INVISIBLE);*/
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View v = inflater.inflate(R.layout.custom_orderlayout, parent, false);
//            TextView itemname = (TextView) v.findViewById(R.id.itemname);
//            TextView dateandtime = (TextView) v.findViewById(R.id.dateandtime);
//            TextView amount = (TextView) v.findViewById(R.id.totalamount);
//            itemname.setText(OrderItemArray.get(position));
//            dateandtime.setText(OrderDateandTimeArray.get(position));
//            amount.setText(OrderAmountArray.get(position));
//
//            /*itemname.setText(OrderItemArray.get(position));
//            dateandtime.setText(OrderDateandTimeArray.get(position));
//            amount.setText(OrderAmountArray.get(position));*/
//            return v;
//
//        }
//    }

}
