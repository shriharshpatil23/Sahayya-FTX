package com.example.ngosocialapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirebasePostAdapter extends FirebaseRecyclerAdapter<Post,FirebasePostAdapter.MyviewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FirebasePostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context=context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.feed_post,parent,false);
        return new MyviewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull Post model) {
        Post post = model;
        Uri ur;
        ur=Uri.parse(post.getPostUrl());
        Glide.with(context).load(ur).into(holder.postMedia);
        holder.caption.setText(post.getCaption());
        holder.ngoName.setText(post.getNgoName());

    }

    public static class MyviewHolder extends RecyclerView.ViewHolder
    {
        ImageView postMedia;
        TextView caption,ngoName;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ngoName = itemView.findViewById(R.id.ngo_name_feed_post);
            caption=itemView.findViewById(R.id.feed_post_caption_comments);
            postMedia =itemView.findViewById(R.id.feed_post_pic);
        }
    }

    private int getRandomColor()
    {
        List<Integer> colorcode=new ArrayList<>();
        colorcode.add(R.color.gray);
        colorcode.add(R.color.pink);
        colorcode.add(R.color.lightgreen);
        colorcode.add(R.color.skyblue);
        colorcode.add(R.color.color1);
        colorcode.add(R.color.color2);
        colorcode.add(R.color.color3);

        colorcode.add(R.color.color4);
        colorcode.add(R.color.color5);
        colorcode.add(R.color.green);

        Random random=new Random();
        int number=random.nextInt(colorcode.size());
        return colorcode.get(number);



    }
}
