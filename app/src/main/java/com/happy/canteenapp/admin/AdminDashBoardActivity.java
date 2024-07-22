package com.happy.canteenapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.happy.canteenapp.Menu;
import com.happy.canteenapp.MyOrders;
import com.happy.canteenapp.R;
import com.happy.canteenapp.SignupActivity;
import com.happy.canteenapp.canteen.CanteenDashBoardActivity;
import com.happy.canteenapp.canteen.CanteenViewOrderActivity;

public class AdminDashBoardActivity extends AppCompatActivity
{
    LinearLayout addUser, viewUser, addMasterButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        addUser = findViewById(R.id.add_user);
        viewUser = findViewById(R.id.view_user);
        addMasterButton = findViewById(R.id.add_master);


        addUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AdminDashBoardActivity.this, AdminAddUserActivity.class));
            }
        });

        addMasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(AdminDashBoardActivity.this, AdminAddMasterActivity.class));
            }
        });

        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoardActivity.this, AdminViewUser.class));
            }
        });
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
                Intent intent = new Intent(getApplicationContext(), MyOrders.class);
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
}
