package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class listitems extends AppCompatActivity {
    ArrayList<Fruitlist> listfruit=new ArrayList<>();
    ListView list;
    Customadapter fruitadapter;
    TextView Fruittotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruitlistitems);

        list=findViewById(R.id.listview);
        listfruit.add(new Fruitlist("Mango",80,0));
        listfruit.add(new Fruitlist("Apple",60,0));
        listfruit.add(new Fruitlist("Watermelon",30,0));
        listfruit.add(new Fruitlist("Banana",35,0));
        listfruit.add(new Fruitlist("Papaya",25,0));
        listfruit.add(new Fruitlist("Pineapple",50,0));
        listfruit.add(new Fruitlist("Orange",30,0));
        listfruit.add(new Fruitlist("Strawberry",100,0));
        listfruit.add(new Fruitlist("Guava",70,0));
        listfruit.add(new Fruitlist("Lychee",80,0));
        listfruit.add(new Fruitlist("Cherry",200,0));
        listfruit.add(new Fruitlist("Grapes",90,0));
        listfruit.add(new Fruitlist("DragonFruit",120,0));
        listfruit.add(new Fruitlist("JackFruit",150,0));
        listfruit.add(new Fruitlist("Kiwi",250,0));
        listfruit.add(new Fruitlist("MuskMelon",40,0));

        fruitadapter=new Customadapter(this,listfruit);
        list.setAdapter(fruitadapter);
        if (savedInstanceState != null) {
            // Restore saved quantities
            fruitadapter.quantityMap = (HashMap<Integer, Integer>) savedInstanceState.getSerializable("quantities");
            fruitadapter.notifyDataSetChanged();
        }

    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("quantities", new HashMap<>(fruitadapter.quantityMap));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fruitadapter.quantityMap = (HashMap<Integer, Integer>) savedInstanceState.getSerializable("quantities");
            fruitadapter.notifyDataSetChanged();
        }
    }
}