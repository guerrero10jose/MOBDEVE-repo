package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.SelectFriendAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;

public class SelectFriend extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Friend> friendArrayList;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private SelectFriendAdapter selectFriendAdapter;
    private String userkey, userImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_friend);
        recyclerView = findViewById(R.id.rv_frndlist);

        Intent OldIntent = getIntent();
        String notes = OldIntent.getStringExtra("Note");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        friendArrayList = new ArrayList<>();
        loadfriends();

        selectFriendAdapter= new SelectFriendAdapter(getApplicationContext(), friendArrayList, notes);
        recyclerView.setAdapter(selectFriendAdapter);
    }

    private void loadfriends() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        SessionManage sessionManage = new SessionManage(getApplicationContext());
        String key = sessionManage.getSession();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                friendArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(key)) {

                        String keys = snapshot.getKey();
                        setKey(keys);

                        if(snapshot.child("friendlist").exists())
                        {
                            for(DataSnapshot snapshot1 : snapshot.child("friendlist").getChildren()) {

                                friendArrayList.add(new Friend(R.drawable.nopic,
                                        snapshot1.child("name").getValue().toString()));
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No friends found", Toast.LENGTH_SHORT).show();
                        }


                        break;
                    }
                }
                selectFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void setKey(String key){
        this.userkey = key;
    }
}
