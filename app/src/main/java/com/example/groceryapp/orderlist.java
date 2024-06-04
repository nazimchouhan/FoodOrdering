package com.example.groceryapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class orderlist implements Parcelable {
    String ordertype;
    int noofitems;
    int ordervalue;
    String hosteladdress;

    public orderlist(String ordertype,int noofitems,int ordervalue,String hosteladdress) {
        this.hosteladdress = hosteladdress;
        this.noofitems=noofitems;
        this.ordertype=ordertype;
        this.ordervalue=ordervalue;

    }

    protected orderlist(Parcel in) {
        ordertype = in.readString();
        noofitems = in.readInt();
        ordervalue = in.readInt();
        hosteladdress = in.readString();
    }

    public static final Creator<orderlist> CREATOR = new Creator<orderlist>() {
        @Override
        public orderlist createFromParcel(Parcel in) {
            return new orderlist(in);
        }

        @Override
        public orderlist[] newArray(int size) {
            return new orderlist[size];
        }
    };

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public int getNoofitems() {
        return noofitems;
    }

    public void setNoofitems(int noofitems) {
        this.noofitems = noofitems;
    }

    public int getOrdervalue() {
        return ordervalue;
    }

    public void setOrdervalue(int ordervalue) {
        this.ordervalue = ordervalue;
    }

    public String getHosteladdress() {
        return hosteladdress;
    }

    public void setHosteladdress(String hosteladdress) {
        this.hosteladdress = hosteladdress;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(ordertype);
        dest.writeInt(noofitems);
        dest.writeInt(ordervalue);
        dest.writeString(hosteladdress);
    }
}