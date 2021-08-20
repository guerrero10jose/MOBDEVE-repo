package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.FriendAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {

    private DataHelper3 dataHelper;
    private ArrayList<Friend> friendArrayList;
    private FriendAdapter friendAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_friends);
        recyclerView = findViewById(R.id.rv_frndlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        populateList();

        friendAdapter = new FriendAdapter(getApplicationContext(), friendArrayList);
        recyclerView.setAdapter(friendAdapter);
    }

    private void populateList() {
        dataHelper = new DataHelper3();
        friendArrayList = dataHelper.initializeData();
    }
}
