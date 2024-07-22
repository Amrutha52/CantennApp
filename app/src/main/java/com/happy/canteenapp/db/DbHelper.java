package com.happy.canteenapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.happy.canteenapp.Const;
import com.happy.canteenapp.modals.OrderInfo;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedMeCanteenDB";
    public static final String TABLE_ORDER_DETAILS = "OrderDetails";

    private SharedPreferences shp;
    private Context context;

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        shp = context.getSharedPreferences(Const.Shared_Pref_name, Context.MODE_PRIVATE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE " + TABLE_ORDER_DETAILS + " (orderItems TEXT,orderedOn TEXT, amount TEXT, userName TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion <= 1) {
//            db.execSQL("CREATE TABLE " + TABLE_USER_DETAILS + " (registrationNumber TEXT,name TEXT,email TEXT, mobileNumber TEXT, department TEXT,userType TEXT, password TEXT,confirmPassword TEXT,isActive int)");
//
//        }
//        if (oldVersion <= 2) {
//            db.execSQL("ALTER TABLE " + TABLE_USER_DETAILS + " ADD createdBy TEXT");
//        }

    }

    public void insertOrderDetails(String orderItems, String orderedOn, String amount, String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put("orderItems", orderItems);
        cv.put("orderedOn", orderedOn);
        cv.put("amount", amount);
        cv.put("userName", userName);

        db.insert(TABLE_ORDER_DETAILS, null, cv);


    }

    public void deleteOrderDetails()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_ORDER_DETAILS);
    }

    public ArrayList<OrderInfo> getOrderData()
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<OrderInfo> resultList = new ArrayList<>();

        Cursor cur = db.rawQuery("select * from " + TABLE_ORDER_DETAILS, null);

        if (cur.getCount() > 0) {

            cur.moveToFirst();

            for (int i = 0; i < cur.getCount(); i++) {

                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderItems(cur.getString(cur.getColumnIndex("orderItems")));
                orderInfo.setOrderedOn(cur.getString(cur.getColumnIndex("orderedOn")));
                orderInfo.setAmount(cur.getString(cur.getColumnIndex("amount")));
                orderInfo.setUserName(cur.getString(cur.getColumnIndex("userName")));

                resultList.add(orderInfo);

                cur.moveToNext();

            }

        }

        return resultList;
    }
}

