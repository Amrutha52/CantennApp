package com.happy.canteenapp.fragmentss;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.happy.canteenapp.Const;
import com.happy.canteenapp.InvoiceActivity;
import com.happy.canteenapp.R;
import com.happy.canteenapp.adapter.PlateAdapter;
import com.happy.canteenapp.db.DbHelper;
import com.happy.canteenapp.modals.FoodDetails;
import com.happy.canteenapp.modals.StorageClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PlateFragment extends Fragment
{
    ListView listview;
    Button placeOrder;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String username;

    SharedPreferences shp;

    CheckBox deliveryOptionCheckBox;

    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plate, container, false);

        dbHelper = new DbHelper(getContext());

        shp = this.getActivity().getSharedPreferences(Const.Shared_Pref_name, MODE_PRIVATE);
        String userType = shp.getString(Const.shp_user_type,"");
        Log.e("Log", "userTypePlateFragment" + userType);

        deliveryOptionCheckBox = view.findViewById(R.id.opt_delivery);

        if (userType.equals("Teacher"))
        {
            deliveryOptionCheckBox.setVisibility(View.VISIBLE);
        }
        else
        {
            deliveryOptionCheckBox.setVisibility(View.INVISIBLE);
        }


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){
            String userEmail = user.getEmail();
            username   = userEmail.substring(0, userEmail.lastIndexOf("@"));
        }

        //get current date and time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("orders/" + username );


        placeOrder = view.findViewById(R.id.placeorder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodDetails> storage = new StorageClass().getFoodCart();
                String orderItems = "";
                int amount=0;
                for(int i=0;i<storage.size();i++){
                    FoodDetails foodItem = storage.get(i);
                    orderItems = orderItems+foodItem.getFoodQuantity()+" X "+foodItem.getFoodName()+" ,";
                    amount = amount + foodItem.getFoodQuantity()*foodItem.getPrice();
                    //Toast.makeText(getContext(),String.valueOf(amount),Toast.LENGTH_LONG).show();
                }
                String key = dbRef.push().getKey();
                dbRef.child(key).child("orderItems").setValue(orderItems);
                dbRef.child(key).child("amount").setValue(String.valueOf(amount));
                dbRef.child(key).child("orderedOn").setValue(formattedDate);

                dbHelper.insertOrderDetails(orderItems,formattedDate, String.valueOf(amount),username);

                Intent intent = new Intent(getContext(), InvoiceActivity.class);
                intent.putExtra("storage",storage);
                /*for(int i=0;i<storage.size();i++)
                    new StorageClass().getFoodCart().remove(i);*/
                startActivity(intent);
            }
        });

        listview = (ListView) view.findViewById(R.id.custom_ListView);
        listview.setAdapter(new PlateAdapter(getContext(),new StorageClass().getFoodCart()));

        FloatingActionButton addItems = (FloatingActionButton) view.findViewById(R.id.addItems);
        listview.setEmptyView(view.findViewById(R.id.empty_view));
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Add Some Items", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return view;
    }



}
