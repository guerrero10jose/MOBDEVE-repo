package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.PostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.UserPostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;

public class IndivPost extends AppCompatActivity {

    private DataHelper dataHelper;
    private ArrayList<Post> postArrayList;
    private UserPostAdapter postAdapter;
    private RecyclerView recyclerView;
    private UserPostAdapter.RecyclerViewClickListener listener;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_posts);
        recyclerView = findViewById(R.id.rv_pstlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //populateList();

        postArrayList = new ArrayList<>();

        getposts();

        setOnClickListener();
        postAdapter = new UserPostAdapter(getApplicationContext(), postArrayList, listener);
        recyclerView.setAdapter(postAdapter);
    }

    private void getposts() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("posts");

        SessionManage sessionManage = new SessionManage(getApplicationContext());

        String key = sessionManage.getSession();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(key)) {

                        Post post = new Post(snapshot.child("modelname").getValue().toString(),
                                Integer.parseInt(snapshot.child("likes").getValue().toString()),
                                Integer.parseInt(snapshot.child("comments").getValue().toString()),
                                snapshot.child("caption").getValue().toString(),
                                snapshot.child("task").getValue().toString(),
                                Boolean.parseBoolean(snapshot.child("liked").getValue().toString()),
                                snapshot.child("username").getValue().toString(),
                                R.drawable.nopic,
                                snapshot.child("postid").getValue().toString());

                        postArrayList.add(post);
                    }
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

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
                intent.putExtra("postid", postArrayList.get(position).getPostid());
                startActivity(intent);
            }
        };
    }
}
