package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.MessageAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Message;

import java.util.ArrayList;

public class Messages extends AppCompatActivity {

    private DataHelper2 dataHelper;
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        recyclerView = findViewById(R.id.rv_msglist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        populateList();

        messageAdapter = new MessageAdapter(getApplicationContext(), messageArrayList);
        recyclerView.setAdapter(messageAdapter);
    }

    private void populateList() {
        dataHelper = new DataHelper2();
        messageArrayList = dataHelper.initializeData();
    }
}
