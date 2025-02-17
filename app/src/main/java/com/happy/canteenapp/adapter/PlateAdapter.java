package com.happy.canteenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.happy.canteenapp.R;
import com.happy.canteenapp.modals.FoodDetails;
import com.happy.canteenapp.modals.StorageClass;

import java.util.ArrayList;

public class PlateAdapter extends ArrayAdapter<FoodDetails> {

    int dummyQuantity;

    public PlateAdapter(Context context, ArrayList<FoodDetails> storage) {
        super(context, 0, storage);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater customInflater = LayoutInflater.from(getContext());

            convertView = customInflater.inflate(R.layout.plate_list_layout, parent, false);
        }
        FoodDetails foodItem = getItem(position);
        /*
        getting the food Quantity ...
         */

        dummyQuantity = foodItem.getFoodQuantity();
        int totalPrice = dummyQuantity * foodItem.getPrice();

        /*
        gettingn the viewc id's
         */
        ImageView ifoodimage = (ImageView) convertView.findViewById(R.id.listimage);
        TextView ifoodName = (TextView) convertView.findViewById(R.id.listname);
        TextView ifoodPrice = (TextView) convertView.findViewById(R.id.selectedprice);


        ifoodimage.setImageResource(foodItem.getFoodImage());
        ifoodName.setText(foodItem.getFoodName());
        ifoodPrice.setText(Integer.toString(totalPrice));


        /*
        setting the tag
         */
        ImageButton delButton = (ImageButton) convertView.findViewById(R.id.delete);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StorageClass().getFoodCart().remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


}
