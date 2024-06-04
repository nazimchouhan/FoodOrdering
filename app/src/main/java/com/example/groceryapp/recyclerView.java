package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerView extends RecyclerView.Adapter<recyclerView.viewHolder> {

    Context context;
    List<orderlist> ordersummarylist;

    public recyclerView(Context context, List<orderlist> ordersummarylist) {
        this.context = context;
        this.ordersummarylist = ordersummarylist != null ? ordersummarylist : new ArrayList<>();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        orderlist order = ordersummarylist.get(position);
        holder.ordertype.setText(order.getOrdertype());
        holder.noofitems.setText(String.valueOf(order.getNoofitems()));
        holder.ordervalue.setText(String.valueOf(order.getOrdervalue()));
        holder.hosteladdress.setText(order.getHosteladdress());
    }

    @Override
    public int getItemCount() {
        return ordersummarylist.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView ordertype, noofitems, ordervalue, hosteladdress;

        public viewHolder(@NonNull View view) {
            super(view);

            ordertype = view.findViewById(R.id.ordertype);
            ordervalue = view.findViewById(R.id.totalamount);
            noofitems = view.findViewById(R.id.noofitem);
            hosteladdress = view.findViewById(R.id.hosteladdress);
        }
    }
}
