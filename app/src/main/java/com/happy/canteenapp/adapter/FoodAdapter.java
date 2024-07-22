package com.happy.canteenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.canteenapp.R;
import com.happy.canteenapp.modals.FoodDetails;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<FoodDetails> {

    public FoodAdapter (Context context, ArrayList<FoodDetails> storage){
        super(context,0,storage);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater customInflater = LayoutInflater.from(getContext());
            convertView = customInflater.inflate(R.layout.menu_food_item,parent,false);
        }

        FoodDetails foodItem = getItem(position);

        ImageView ifoodimage = (ImageView) convertView.findViewById(R.id.foodimage);
        TextView ifoodName = (TextView) convertView.findViewById(R.id.foodname);
        TextView ifoodPrice = (TextView) convertView.findViewById(R.id.foodprice);

        ifoodimage.setImageResource(foodItem.getFoodImage());
        ifoodName.setText(foodItem.getFoodName());
        ifoodPrice.setText(Integer.toString(foodItem.getPrice()));

        return convertView;
    }
}

