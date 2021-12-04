package com.example.ngosocialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventsByNGO extends AppCompatActivity {


    private RecyclerView recyclerView;
    private DatabaseReference database;
    private eventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_by_ngo);
        Intent i = getIntent();
        String userId = i.getStringExtra("userID");

//        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_SHORT).show();
        recyclerView=findViewById(R.id.show_Events_by_Ngo);
        database=  FirebaseDatabase.getInstance().getReference("event").child(userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<event> options=
                new FirebaseRecyclerOptions.Builder<event>()
                        .setQuery(database,event.class)
                        .build();
        adapter=new eventAdapter(options,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}