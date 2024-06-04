package com.example.groceryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.System.in;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.razorpay.Checkout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class fruitchaat extends AppCompatActivity implements PaymentResultListener{
    private static final int UPI_PAYMENT_REQUEST_CODE = 1;
    EditText fruitchaatquantity;
    ArrayList<String> hostellist = new ArrayList<>();
    ArrayList<Integer> favfruitlist = new ArrayList<>();
    TextView Totalchaat;
    TextView paymentrequest;
    int totalAmount;
    ProgressBar progressbar;
    int quantity;
    String selectedhostel;
    boolean[] fruitboolean;
    String[] favouritefruitarr = {"Mango", "Watermelon", "Papaya", "Pineapple", "Grapes", "Apple", "Banana"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruitchaat);

        fruitboolean = new boolean[favouritefruitarr.length];
        Totalchaat = findViewById(R.id.fruitchaattotal);
        TextView favouritefruit = findViewById(R.id.favouritefruit);
        progressbar=findViewById(R.id.progressbarpayment);
        favouritefruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fruitchaat.this);
                builder.setTitle("Choose only 4 of them");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(favouritefruitarr, fruitboolean, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            favfruitlist.add(which);
                        } else {
                            favfruitlist.remove(Integer.valueOf(which));
                        }
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringbuilder = new StringBuilder();
                        for (int j = 0; j < favfruitlist.size(); j++) {
                            stringbuilder.append(favouritefruitarr[favfruitlist.get(j)]);

                            if (j != favfruitlist.size() - 1) {
                                stringbuilder.append(",");
                            } else {
                                stringbuilder.append(" ");
                            }
                        }
                        favouritefruit.setText(stringbuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < fruitboolean.length; j++) {
                            // remove all selection
                            fruitboolean[j] = false;
                            // clear language list
                            favfruitlist.clear();
                            // clear text view value
                            favouritefruit.setText("");
                        }
                    }
                });
                builder.show();
            }

        });

        fruitchaatquantity = findViewById(R.id.fruitchaatquantity);
        paymentrequest = findViewById(R.id.paymentrequest);
        Totalchaat = findViewById(R.id.fruitchaattotal);
        Spinner hostelspinner = findViewById(R.id.hostelspinner);

        fruitchaatquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalAmount(s.toString());
            }
        });
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
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hostellist);
        hostelspinner.setAdapter(spinneradapter);
        hostelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedhostel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Checkout.preload(getApplicationContext());
        paymentrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalAmount!=0){
                    progressbar.setVisibility(View.VISIBLE);
                    paymentrequest.setVisibility(View.GONE);
                    Checkout.preload(getApplicationContext());
                    initiateupipayment();
                }
                else{
                    Toast.makeText(fruitchaat.this, "Please Enter the Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateTotalAmount(String quantityText) {
        quantity = 0;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            // Handle invalid input gracefully
            e.printStackTrace();
        }
        totalAmount = quantity * 30;
        Totalchaat.setText(String.valueOf(totalAmount));
    }

    private void initiateupipayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_DPWrwhFavbaKII");

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
            options.put("amount", totalAmount * 100); // pass amount in currency subunits

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
        progressbar.setVisibility(View.GONE);
        paymentrequest.setVisibility(View.VISIBLE);
        Toast.makeText(this,"Payment Successfull",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, drawerclass.class);
        intent.putExtra("noofitem",quantity);
        intent.putExtra("value",Integer.parseInt(Totalchaat.getText().toString()));
        intent.putExtra("type","Fruit Chaat");
        intent.putExtra("Hostel",selectedhostel);
        intent.putExtra("paymentstatus","Successfull");
        startActivity(intent);
    }
    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show();
        progressbar.setVisibility(View.GONE);
        paymentrequest.setVisibility(View.VISIBLE);
    }
}

