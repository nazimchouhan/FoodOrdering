package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText mobileno;
    Button otpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if(isLoggedIn==true){
            Intent intent=new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        mobileno=findViewById(R.id.mobileno);
        mobileno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mobileno.setText("");
                }
            }
        });
        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileno.setText("");
            }
        });
        otpbutton=findViewById(R.id.otpbutton);
        ProgressBar progressbar=findViewById(R.id.progressbar_sendingotp);
        otpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mobileno.getText().toString().trim().isEmpty()){
                    if(mobileno.getText().toString().trim().length()==10){
                        progressbar.setVisibility(View.VISIBLE);
                        otpbutton.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobileno.getText().toString(),
                                60, TimeUnit.SECONDS,
                                MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressbar.setVisibility(View.GONE);
                                        otpbutton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressbar.setVisibility(View.GONE);
                                        otpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, "e.getMessage()", Toast.LENGTH_SHORT).show();
                                    }

                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressbar.setVisibility(View.GONE);
                                        otpbutton.setVisibility(View.VISIBLE);
                                        Intent intent=new Intent(MainActivity.this, otpverify.class);
                                        intent.putExtra("userotp",backendotp);
                                        intent.putExtra("mobileno",mobileno.getText().toString());
                                        startActivity(intent);
                                    }
                                });

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Enter Correct Number",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter complete Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}