package com.happy.canteenapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInfo
{
    @SerializedName("orderItems")
    @Expose
    private String orderItems;

    @SerializedName("orderedOn")
    @Expose
    private String orderedOn;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("userName")
    @Expose
    private String userName;

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}



