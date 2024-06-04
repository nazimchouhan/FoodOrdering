package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class orderSummary extends Fragment {
    RecyclerView RecyclerView;
    ArrayList<orderlist> orderlist;
    Database database;
    public orderSummary() {
        // Required empty public constructor
    }
    public static orderSummary newInstance(ArrayList<orderlist> orderlist) {
        orderSummary fragment = new orderSummary();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("orders",orderlist);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderlist=getArguments().getParcelableArrayList("orders");
        }
        else{
            orderlist=new ArrayList<>();
        }
        database=new Database(getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order_summary, container, false);
        RecyclerView=view.findViewById(R.id.recycler);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderlist= database.getorders();
        Log.d(TAG, "ordersummary orderlist is:" + orderlist);
        recyclerView adapter=new recyclerView(getContext(),orderlist);
        RecyclerView.setAdapter(adapter);
        return view;
    }
}

