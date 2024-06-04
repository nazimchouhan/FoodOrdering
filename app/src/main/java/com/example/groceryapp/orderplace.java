package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class orderplace extends AppCompatActivity implements PaymentResultListener {
    ArrayList<String> hostellist=new ArrayList<>();
    Spinner spinner;
    TextView paymentprocess;
    TextView listtotal;
    ProgressBar progressbar;
    String selectedhostel;
    String quantity;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderplace);
        spinner=findViewById(R.id.spinner);
        listtotal=findViewById(R.id.fruitlisttotal);
        progressbar=findViewById(R.id.progressbarorder);
        hostellist.add("Vivekanand");
        hostellist.add("Limbdi");
        hostellist.add("Vishwasariya");
        hostellist.add("Rajputana");
        hostellist.add("Dhanrajgiri");
        hostellist.add("Dhanrajgiri-2");
        hostellist.add("Morvi");
        hostellist.add("Morvi-2");
        hostellist.add("CV Raman");
        hostellist.add("SN Bose ");
        hostellist.add("Ramanujan");
        hostellist.add("Aryabhatta");
        ArrayAdapter<String> spinneradapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,hostellist);
        spinner.setAdapter(spinneradapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedhostel=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listtotal.setText(getIntent().getStringExtra("item_data"));
        Log.d(TAG,"totalamount is :" + listtotal.getText().toString());

        quantity= String.valueOf(getIntent().getIntExtra("quantity",0));
        Log.d(TAG,"quantity is :" + quantity);
        paymentprocess=findViewById(R.id.paymentprocess);
        paymentprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(listtotal.getText().toString())!=0){
                    progressbar.setVisibility(View.VISIBLE);
                    paymentprocess.setVisibility(View.GONE);
                    Checkout.preload(getApplicationContext());
                    initiateupipayment();
                }
            }
        });
    }
    private void initiateupipayment(){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_DPWrwhFavbaKII");
        int totalamount=Integer.parseInt(listtotal.getText().toString());

        // Optional: set image
        checkout.setImage(R.drawable.tfruit);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Fruit Chaat");
            options.put("description", "Confirm Order");
            options.put("image", "http://example.com/image/rzp.jpg");
            options.put("theme.color", "#8DE823");
            options.put("currency", "INR");
            options.put("amount", totalamount * 100); // pass amount in currency subunits

            options.put("prefill.email", "nazimchouhan49@gmail.com");
            options.put("prefill.contact", "8058086931");

            // Include method options to show UPI
            JSONObject method = new JSONObject();

            method.put("upi", true);
            method.put("card", true);
            method.put("netbanking", true);
            method.put("wallet", true);
            options.put("method", method);
            Log.d(TAG, "Payment options: " + options.toString()); // Log the options JSON

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this,"Payment Successfull",Toast.LENGTH_SHORT).show();
        progressbar.setVisibility(View.GONE);
        paymentprocess.setVisibility(View.VISIBLE);
        try {
            Intent intent = new Intent(this, drawerclass.class);
            intent.putExtra("noofitem", Integer.parseInt(quantity));
            intent.putExtra("value", Integer.parseInt(listtotal.getText().toString()));
            intent.putExtra("type", "Fruit Item");
            intent.putExtra("Hostel", selectedhostel);
            intent.putExtra("paymentstatus", "Successful");
            Log.d(TAG, "Starting drawerclass activity with intent: " + intent.toString());
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error starting drawerclass activity", e);
        }
    }
    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show();
        progressbar.setVisibility(View.GONE);
        paymentprocess.setVisibility(View.VISIBLE);
    }
}




