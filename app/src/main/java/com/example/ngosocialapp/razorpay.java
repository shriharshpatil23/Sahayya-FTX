package com.example.ngosocialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.protobuf.StringValue;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class razorpay extends AppCompatActivity implements PaymentResultListener {

    private Button payBtn;
    private TextInputEditText amount;
    private NGO curNgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        payBtn=findViewById(R.id.razorBtn);
        amount=findViewById(R.id.razorpayamount);
        Intent i=getIntent();
        curNgo=(NGO) i.getSerializableExtra("ngoObj");
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().length()>0)
                {
                    paySIP();
                }
            }
        });
    }
    private void paySIP() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ivBFA9HN6Hnm0o");
        checkout.setImage(R.drawable.ngologo);

        JSONObject object = new JSONObject();
        try{
            object.put("name",curNgo.getName());
            object.put("description","Donation to "+curNgo.getName());
            object.put("theme.color", "");
            object.put("currency", "INR");
            object.put("amount", (Integer.parseInt(amount.getText().toString())*100));
            object.put("prefill.contact", curNgo.getPhone());
            object.put("prefill.email", curNgo.getEmail());

            // opening razorpay's  checkout activity
            checkout.open(razorpay.this, object);
        }
        catch (Exception e){
            Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Snackbar.make(findViewById(android.R.id.content), "Payment Successful", Snackbar.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "successfull !\nTx ID - " + s, Toast.LENGTH_LONG).show();
//        Intent i =new Intent(LoanRepaymentActivity.this,MainActivity.class);
//        startActivity(i);
        Snackbar.make(findViewById(android.R.id.content), "Payment Successful", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}