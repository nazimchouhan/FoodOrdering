package com.example.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class viewpager extends FragmentPagerAdapter {
    private ArrayList<orderlist> orders;
    private ArrayList<statuslist> liststatus;
    public viewpager(@NonNull FragmentManager fm ,ArrayList<orderlist> orders,ArrayList<statuslist> liststatus) {
        super(fm);
        this.orders=orders;
        this.liststatus=liststatus;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){

            return orderSummary.newInstance(orders);
        }
        else{
            return orderStatus.newInstance(liststatus);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Order Summary";
        }
        else{
            return "Order Status";
        }
    }
}
