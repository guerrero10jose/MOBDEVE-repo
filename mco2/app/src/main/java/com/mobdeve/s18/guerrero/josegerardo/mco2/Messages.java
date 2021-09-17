package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.MessageAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Message;

import java.util.ArrayList;

public class Messages extends AppCompatActivity {
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        recyclerView = findViewById(R.id.rv_msglist);

        messageArrayList = new ArrayList<>();
        loadmessages();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        messageAdapter = new MessageAdapter(getApplicationContext(), messageArrayList);
        recyclerView.setAdapter(messageAdapter);
    }


    private void loadmessages() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        SessionManage sessionManage = new SessionManage(getApplicationContext());
        String key = sessionManage.getSession();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                messageArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(key)) {

                        if(snapshot.child("messages").exists())
                        {
                            for(DataSnapshot snapshot1 : snapshot.child("messages").getChildren()) {

                                Log.v("here", "inside second " + snapshot1);
                                Message temp = new Message(snapshot1.child("username").getValue().toString(), snapshot1.child("message").getValue().toString(),
                                        Integer.parseInt(snapshot1.child("userImageId").getValue().toString()), snapshot1.child("messageId").getValue().toString());

                                messageArrayList.add(temp);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No message found", Toast.LENGTH_SHORT).show();
                        }


                        break;
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
