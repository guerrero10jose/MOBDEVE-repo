package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.PostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.UserPostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;

public class IndivPost extends AppCompatActivity {

    private DataHelper dataHelper;
    private ArrayList<Post> postArrayList;
    private UserPostAdapter postAdapter;
    private RecyclerView recyclerView;
    private UserPostAdapter.RecyclerViewClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_posts);
        recyclerView = findViewById(R.id.rv_pstlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        populateList();

        setOnClickListener();
        postAdapter = new UserPostAdapter(getApplicationContext(), postArrayList, listener);
        recyclerView.setAdapter(postAdapter);
    }

    private void populateList() {
        dataHelper = new DataHelper();
        postArrayList = dataHelper.initializeData();
    }

    private void setOnClickListener() {
        listener = new UserPostAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), MyPostSingle.class);
                startActivity(intent);
            }
        };
    }
}
