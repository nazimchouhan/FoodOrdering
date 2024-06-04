package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

@SuppressLint("ParcelCreator")
public class statuslist implements Parcelable {
    String paymentstatus;
    String deliverystatus;

    public statuslist(String paymentstatus,String deliverystatus) {
        this.deliverystatus = deliverystatus;
        this.paymentstatus=paymentstatus;
    }
    protected statuslist(Parcel in) {
        paymentstatus = in.readString();
        deliverystatus = in.readString();
    }
    public static final Creator<statuslist> CREATOR = new Creator<statuslist>() {
        @Override
        public statuslist createFromParcel(Parcel in) {
            return new statuslist(in);
        }

        @Override
        public statuslist[] newArray(int size) {
            return new statuslist[size];
        }
    };


    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(paymentstatus);
        dest.writeString(deliverystatus);

    }
}
