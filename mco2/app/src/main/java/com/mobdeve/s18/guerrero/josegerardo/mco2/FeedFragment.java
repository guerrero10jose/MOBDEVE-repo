package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.PostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private DataHelper dataHelper;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;
    private PostAdapter.RecyclerViewClickListener listener;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_feedlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        populateList();

        setOnClickListener();
        postAdapter = new PostAdapter(this.getContext(), postArrayList, listener);

        recyclerView.setAdapter(postAdapter);

        return view;
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
                intent.putExtra("user_pic", postArrayList.get(position).getUserImageId());
                intent.putExtra("cap_pic", postArrayList.get(position).getImageId());
                startActivity(intent);
            }
        };
    }

    private void populateList() {
        dataHelper = new DataHelper();
        postArrayList = dataHelper.initializeData();
    }
}