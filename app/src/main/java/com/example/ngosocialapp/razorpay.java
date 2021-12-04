package com.example.ngosocialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.StringValue;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class razorpay extends AppCompatActivity implements PaymentResultListener {

    private Button payBtn;
    private TextInputEditText amount;
    private NGO curNgo;
    private DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        payBtn=findViewById(R.id.razorBtn);
        amount=findViewById(R.id.razorpayamount);
        Intent i=getIntent();
        curNgo=(NGO) i.getSerializableExtra("ngoObj");
        databaseUsers= FirebaseDatabase.getInstance().getReference("transaction");
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

        String user= FirebaseAuth.getInstance().getUid();
        String ngo=curNgo.getName();
        transaction tra=new transaction(user,ngo,amount.getText().toString());
        databaseUsers.child(user).push().setValue(tra);
        databaseUsers.child(ngo).push().setValue(tra);
        Intent j=new Intent(getApplicationContext(),splashAfterTran.class);
        startActivity(j);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}