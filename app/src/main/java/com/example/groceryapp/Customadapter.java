package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customadapter extends BaseAdapter {
    Context context;
    ArrayList<Fruitlist> listfruit;
    Map<Integer, Integer> quantityMap = new HashMap<>();

    EditText fruitquantity;

    int backprice;
    public Customadapter(Context context, ArrayList<Fruitlist> listfruit) {
        this.context = context;
        this.listfruit = listfruit;
    }
    @Override

    public int getCount() {
        return listfruit.size();
    }

    @Override
    public Object getItem(int position) {
        return listfruit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fruitlist, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
         Fruitlist fruit = (Fruitlist)getItem(position);
         viewHolder.fruitname.setText(fruit.getFruitname());
         viewHolder.fruitprice.setText(String.valueOf(fruit.getPrice()));

        if (quantityMap.containsKey(position)) {
            viewHolder.fruitquantity.setText(String.valueOf(quantityMap.get(position)));
        } else {
            viewHolder.fruitquantity.setText(String.valueOf(fruit.getQuantity()));
        }

        viewHolder.fruitquantity.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {
                 int quantity=0;
                 try{
                     quantity = Integer.parseInt(s.toString());
                     quantityMap.put(position, quantity);


                 }
                 catch (NumberFormatException e){
                     // Handle invalid input
                     e.printStackTrace();
                 }
                 int totalAmount = quantity * fruit.getPrice();
                 viewHolder.fruittotal.setText(String.valueOf(totalAmount));
                 // Update the total amount in the Fruitlist object
                 fruit.setQuantity(quantity);
                 fruit.setTotal(totalAmount);
             }
         });
        viewHolder.fruittotal.setText(String.valueOf(fruit.getTotal()));
        convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = quantityMap.containsKey(position) ? quantityMap.get(position) : fruit.getQuantity();
                    if(Integer.parseInt((String) viewHolder.fruittotal.getText())!=0){
                        Intent intent = new Intent(context, orderplace.class);
                        intent.putExtra("item_data", viewHolder.fruittotal.getText());
                        intent.putExtra("quantity",quantity);
                        context.startActivity(intent);
                    }
                }
        });

        return convertView;
    }
    private class ViewHolder {
        TextView fruitname;
        TextView fruitprice;
        EditText fruitquantity;
        TextView fruittotal;

        ViewHolder(View view) {
            fruitname = view.findViewById(R.id.fruitname);
            fruitprice = view.findViewById(R.id.fruitprice);
            fruitquantity = view.findViewById(R.id.fruitquantity);
            fruittotal = view.findViewById(R.id.fruittotal);
        }
    }
}
