package com.happy.canteenapp.admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.happy.canteenapp.R;

import java.util.ArrayList;

public class AdminViewUser extends AppCompatActivity
{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private ListView userListView;

    private int selectedPosition = 0;


    ArrayList<String> listUsers = new ArrayList<String>();
    ArrayList<String> listUserTypes = new ArrayList<String>();
    ArrayList<String> listOrderAmount = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ViewUserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        getSupportActionBar().setTitle("User List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        dbRef = database.getReference("users");

        userListView = findViewById(R.id.user_ListView);

        adapter = new ViewUserAdapter(AdminViewUser.this,android.R.layout.simple_list_item_1, listUsers,listUserTypes);
        userListView.setAdapter(adapter);

        addChildEventListener();

    }


    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /*adapter.add(
                        (String) dataSnapshot.child("itemname").getValue()+" "(String) dataSnapshot.child("price").getValue());*/
                //adapter.add(
                //(String) dataSnapshot.child("itemname").getValue());
                listUsers.add((String) dataSnapshot.child("username").getValue());
                listUserTypes.add((String) dataSnapshot.child("usertype").getValue());

                Log.e("Log", "userNames" + listUsers);
                Log.e("Log", "userTypes" + listUserTypes);

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
                    listUsers.remove(index);
                    listUserTypes.remove(index);

                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

        };
        dbRef.addChildEventListener(childListener);
    }


    class ViewUserAdapter extends ArrayAdapter {
        ArrayList<String> userArray,userTypeArray;

        public ViewUserAdapter(Context c, int x, ArrayList<String> listUsers, ArrayList<String> listUserTypes) {
            super(c, R.layout.view_user_layout, R.id.username,listUsers);
            this.userArray = listUsers;
            this.userTypeArray = listUserTypes;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            /*if(convertView==null)
                emptyText.setVisibility(View.VISIBLE);
            else
                emptyText.setVisibility(View.INVISIBLE);*/
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.view_user_layout, parent, false);
            TextView username = (TextView) v.findViewById(R.id.listname);
            TextView userType = (TextView) v.findViewById(R.id.userTypeTV);
            ImageButton deleteUserButton = v.findViewById(R.id.delete);

            username.setText(userArray.get(position));
            userType.setText(userTypeArray.get(position));

            deleteUserButton.setTag(R.string.key_one, position);
            deleteUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(R.string.key_one);
                    Log.e("Log", "position" + position);
                    deleteItem(v, position);
                }
            });


            /*itemname.setText(OrderItemArray.get(position));
            dateandtime.setText(OrderDateandTimeArray.get(position));
            amount.setText(OrderAmountArray.get(position));*/
            return v;

        }
    }

    public void deleteItem(View view, int position)
    {
        userListView.setItemChecked(position, false);
        dbRef.child(listKeys.get(position)).removeValue();
    }
}
