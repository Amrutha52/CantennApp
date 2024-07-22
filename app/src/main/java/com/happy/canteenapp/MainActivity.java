package com.happy.canteenapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.happy.canteenapp.fragmentss.ItemsFragment;
import com.happy.canteenapp.fragmentss.PlateFragment;
import com.happy.canteenapp.fragmentss.ProfileFragment;
import com.happy.canteenapp.modals.StorageClass;

public class MainActivity extends AppCompatActivity
{
    // double tapping feature
    boolean backPressed ;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    String name = user.getEmail();
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                }
            }
        };

        StorageClass foodStorage = new StorageClass();
        Intent intent = getIntent();

         /*
        setting data on creation of app
        */  foodStorage.setCatalogData();      //setting the data

        View view = findViewById(R.id.home);
        homeButton(view);
        //}
        backPressed = false;

    }

    @Override
    public void onBackPressed()
    {
        Fragment checking = getSupportFragmentManager().findFragmentByTag("Main_Menu");
        if (checking!=null && checking.isVisible()){
            if (backPressed){
                super.onBackPressed();
            }
            else {
                backPressed = true;
                Toast.makeText(this,"Press Again To Exit", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            ItemsFragment itemsFragment = new ItemsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, itemsFragment,"Main_Menu");

            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/

            ft.commit();
            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));

        }
    }

    /*
    when home button is pressed below method executes ...
     */
    public void homeButton(View view) {
        //buttonHome.setTextColor(Color.parseColor("#0000ff"));
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("Main_Menu");
        if (checking==null || !checking.isVisible()){

            //Toast.makeText(this,"Menu Pressed",Toast.LENGTH_SHORT).show();
            ItemsFragment itemsFragment = new ItemsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, itemsFragment,"Main_Menu");
            ft.commit();
            /*
            checking the pop buttomns
            */

            //ft.addToBackStack("Main_Menu");

            //getSupportFragmentManager().popBackStack();

            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
    }

    /*
 when PLATE button is pressed below method executes ...
  */
    public void plateButton(View view) {
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("PLATE_VIEW");
        if (checking==null || !checking.isVisible()) {

            //Toast.makeText(this,"Plate Pressed",Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            PlateFragment plateFragment = new PlateFragment();
            ft.replace(R.id.container, plateFragment, "PLATE_VIEW");


            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/
            ft.commit();
            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
    }

    public void profileButton(View view) {
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("PROFILE_VIEW");
        if (checking==null || !checking.isVisible()) {

            //Toast.makeText(this,"Profile Pressed",Toast.LENGTH_SHORT).show();
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, profileFragment, "PROFILE_VIEW");


            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/
            ft.commit();

            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

//        int id = item.getItemId();
//        {
//            if (id == R.id.item1) {
//                Intent intent = new Intent(MainActivity.this, MyOrders.class);
//                startActivity(intent);
//                //Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
//
//
//            } else if (id == R.id.item2) {
//                signOut();
//                Toast.makeText(this, "Signout", Toast.LENGTH_SHORT).show();
//
//            } else if (id == R.id.item3) {
//                finishAffinity();
//
//            }
//        }

        switch (item.getItemId()) {
            default:
                finish();
                break;

            case R.id.item1:
                Intent intent = new Intent(MainActivity.this,MyOrders.class);
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

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
