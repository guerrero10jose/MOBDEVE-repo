package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.PostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private DataHelper dataHelper;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;
    private PostAdapter.RecyclerViewClickListener listener;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_feedlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        postArrayList = new ArrayList<>();

        //populateList();

        getposts();

        setOnClickListener();
        postAdapter = new PostAdapter(this.getContext(), postArrayList, listener);

        recyclerView.setAdapter(postAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setOnClickListener() {
        listener = new PostAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String Likes = String.valueOf(postArrayList.get(position).getLikes()) + " LIKES";

                Intent intent = new Intent(getContext(), PostSingle.class);

                intent.putExtra("username", postArrayList.get(position).getUsername());
                intent.putExtra("task", postArrayList.get(position).getTask());
                intent.putExtra("caption", postArrayList.get(position).getCaption());
                intent.putExtra("likes", Likes);
                intent.putExtra("user_pic", R.drawable.nopic);
                intent.putExtra("cap_pic", postArrayList.get(position).getModelname());
                intent.putExtra("postid", postArrayList.get(position).getPostid());
                intent.putExtra("com_count", postArrayList.get(position).getComments());
                startActivity(intent);
            }
        };
    }

    private void getposts() {
        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

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
}