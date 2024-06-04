package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class fruitjuice extends AppCompatActivity {
    ArrayList<Fruitlist> listfruit=new ArrayList<>();
    ListView list;
    Customadapter fruitadapter;
    TextView next;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruitjuice);
        list=findViewById(R.id.juicelist);
        next=findViewById(R.id.juicenext);
        listfruit.add(new Fruitlist("Mango",40,0));
        listfruit.add(new Fruitlist("Apple",30,0));
        listfruit.add(new Fruitlist("Watermelon",30,0));
        listfruit.add(new Fruitlist("Banana",30,0));
        listfruit.add(new Fruitlist("Papaya",30,0));
        listfruit.add(new Fruitlist("Pineapple",50,0));
        listfruit.add(new Fruitlist("Orange",30,0));
        listfruit.add(new Fruitlist("Strawberry",70,0));
        listfruit.add(new Fruitlist("Guava",70,0));
        listfruit.add(new Fruitlist("Lychee",80,0));
        listfruit.add(new Fruitlist("Cherry",60,0));
        listfruit.add(new Fruitlist("Grapes",50,0));
        listfruit.add(new Fruitlist("DragonFruit",100,0));
        listfruit.add(new Fruitlist("JackFruit",150,0));
        listfruit.add(new Fruitlist("Kiwi",120,0));
        listfruit.add(new Fruitlist("MuskMelon",40,0));

        fruitadapter=new Customadapter(this,listfruit);
        list.setAdapter(fruitadapter);
    }
}
