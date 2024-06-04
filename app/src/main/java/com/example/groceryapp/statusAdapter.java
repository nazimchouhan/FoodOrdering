package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class statusAdapter extends RecyclerView.Adapter<statusAdapter.viewholder> {
    Context context;
    ArrayList<statuslist> liststatussummary;

    public statusAdapter(Context context, ArrayList<statuslist> liststatussummary) {
        this.context=context;
        this.liststatussummary = liststatussummary != null ? liststatussummary : new ArrayList<>();
    }

    @Override

    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.statuslist,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        statuslist status=liststatussummary.get(position);
        holder.paymentstatus.setText(String.valueOf(status.getPaymentstatus()));
        holder.deliverystatus.setText(String.valueOf(status.getDeliverystatus()));

    }

    @Override
    public int getItemCount() {
        return liststatussummary.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView paymentstatus,deliverystatus;
        public viewholder(@NonNull View view) {
            super(view);
            paymentstatus=view.findViewById(R.id.paymentstatus);
            deliverystatus=view.findViewById(R.id.deliverystatus);
        }
    }
}
