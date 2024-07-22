package com.happy.canteenapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.happy.canteenapp.R;

public class CanteenOrderViewHolder extends RecyclerView.ViewHolder
{
    public TextView itemNameTV, dateTimeTV, totalAmountTV, orderStatusTV;

    public CanteenOrderViewHolder(@NonNull View itemView)
    {
        super(itemView);

        itemNameTV = itemView.findViewById(R.id.itemname);
        dateTimeTV = itemView.findViewById(R.id.dateandtime);
        totalAmountTV = itemView.findViewById(R.id.totalamount);
        orderStatusTV = itemView.findViewById(R.id.orderstatus);

    }
}
