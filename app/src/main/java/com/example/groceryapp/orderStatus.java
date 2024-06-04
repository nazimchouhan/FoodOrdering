package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class orderStatus extends Fragment {
    RecyclerView RecyclerView;
    ArrayList<statuslist> statuslist;
    Database database;
    public orderStatus() {
        // Required empty public constructor
    }
    public static orderStatus newInstance(ArrayList<statuslist> statuslist) {
        orderStatus fragment = new orderStatus();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("liststatus",statuslist);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            statuslist=getArguments().getParcelableArrayList("liststatus");
        }
        else{
            statuslist=new ArrayList<>();
        }
        database=new Database(getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order_status, container, false);
        RecyclerView=view.findViewById(R.id.recyclerview);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statuslist= database.getstatus();
        Log.d(TAG,"oderstatus statuslist is:" + statuslist);
        statusAdapter adapter=new statusAdapter(getContext(),statuslist);
        RecyclerView.setAdapter(adapter);
        return view;
    }
}



