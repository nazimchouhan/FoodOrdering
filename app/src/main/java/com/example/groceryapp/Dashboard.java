package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationview;
    Toolbar toolbar;
    TextView fruit,fruitChaat,fruitjuice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationview=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        fruit=findViewById(R.id.dashboardfruit);
        fruitChaat=findViewById(R.id.dashboardfruitchaat);
        fruitjuice=findViewById(R.id.dashboardfruitjuice);

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, listitems.class);
                startActivity(intent);
            }
        });
        fruitChaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, fruitchaat.class);
                startActivity(intent);
            }
        });
        fruitjuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,fruitjuice.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("HOME");
        }
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.ClosedDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.Ordersummary || id==R.id.Orderstatus){
                    Intent intent=new Intent(Dashboard.this, drawerclass.class);
                    startActivity(intent);
                }

                else if(id==R.id.Logout){
                    handleLogout();
                }
                else if(id==R.id.contactus){
                    handleCall();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void handleCall(){
        String phoneNumber = "tel:+918058086931";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }
    private void handleLogout() {
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isloggedIn",false);
        editor.clear();
        editor.apply();

        Intent intent = new Intent(Dashboard.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override

    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
       }
       else{
          super.onBackPressed();
       }
    }
}
