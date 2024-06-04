package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class drawerclass extends AppCompatActivity {
    private static final String TAG = "drawerclass";
    private ArrayList<orderlist> orders;
    private ArrayList<statuslist> liststatus;
    private Database database;

   FrameLayout framelayout;
    @SuppressLint({"MissingInflatedId", "NewApi", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerclass);
        database=new Database(getApplicationContext());

        orders = new ArrayList<>();
        liststatus = new ArrayList<>();
        if (savedInstanceState != null) {
            orders = savedInstanceState.getParcelableArrayList("orders");
            liststatus = savedInstanceState.getParcelableArrayList("liststatus");
            Log.d(TAG, "Restored orders: " + orders);
            Log.d(TAG, "Restored liststatus: " + liststatus);
        }
        else{
            Intent intent = getIntent();
            String ordertype = intent.getStringExtra("type");
            int noofitem = intent.getIntExtra("noofitem", 0);
            int ordervalue = intent.getIntExtra("value", 0);
            String Hostel = intent.getStringExtra("Hostel");
            String paymentstatus = intent.getStringExtra("paymentstatus");
            String deliverystatus = "Pending";

            if (ordertype != null && Hostel != null && paymentstatus != null){
                orderlist neworder=new orderlist(ordertype,noofitem,ordervalue,Hostel);
                database.addOrder(neworder);
                orders=database.getorders();

                statuslist newstatus=new statuslist(paymentstatus,deliverystatus);
                database.addstatus(newstatus);
                liststatus=database.getstatus();

                Log.d(TAG, "Created new orders: " + orders);
                Log.d(TAG, "Created new liststatus: " + liststatus);


            }


        }
        TabLayout tablayout=findViewById(R.id.tablayout);
        ViewPager viewPager=findViewById(R.id.viewpager);
        viewpager pageradapter=new viewpager(getSupportFragmentManager(),orders,liststatus);
        viewPager.setAdapter(pageradapter);
        tablayout.setupWithViewPager(viewPager);

    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("orders", orders);
        outState.putParcelableArrayList("liststatus", liststatus);

    }
}
