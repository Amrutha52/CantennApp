package com.happy.canteenapp.canteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.happy.canteenapp.Menu;
import com.happy.canteenapp.R;

public class CanteenDashBoardActivity extends AppCompatActivity
{
    LinearLayout addMenuButton, viewButton, addMasterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_dashboard);

        addMenuButton = findViewById(R.id.add_user);
        viewButton = findViewById(R.id.view_user);
        addMasterButton = findViewById(R.id.add_master);


        addMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CanteenDashBoardActivity.this, Menu.class));
            }
        });

        addMasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(AdminDashBoardActivity.this, AdminAddMasterActivity.class));
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CanteenDashBoardActivity.this, CanteenViewOrderActivity.class));
            }
        });
    }
}
