package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.FriendAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {

    private DataHelper3 dataHelper;
    private ArrayList<Friend> friendArrayList;
    private FriendAdapter friendAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String userkey;
    private Button btn_add;
    private EditText txt_fld;
    private int sent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_friends);
        recyclerView = findViewById(R.id.rv_frndlist);

        // button for add friend based on email or username

        btn_add = findViewById(R.id.btn_add);
        txt_fld = findViewById(R.id.txtfld_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //populateList();

        friendArrayList = new ArrayList<>();
        loadfriends();

        friendAdapter = new FriendAdapter(getApplicationContext(), friendArrayList);
        recyclerView.setAdapter(friendAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = txt_fld.getText().toString();

                //Toast.makeText(Friends.this, key, Toast.LENGTH_SHORT).show();
                addUser(key, friendArrayList);
                //Log.v("test", Integer.toString(friendArrayList.size()));
            }
        });

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

                        for(DataSnapshot snapshot1 : snapshot.child("friendlist").getChildren()) {

                            friendArrayList.add(new Friend(R.drawable.nopic,
                                    snapshot1.child("name").getValue().toString()));
                        }

                        break;
                    }
                }

                friendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addUser(String key, ArrayList<Friend> arrList) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        if(key.length() < 1) {
            Toast.makeText(this, "Please input", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionManage sessionManage = new SessionManage(getApplicationContext());
        String user = sessionManage.getSession();

        sent = 0;
        Friend friend = new Friend(R.drawable.nopic, key);

        for(int i = 0; i < arrList.size(); i++) {

            if(arrList.get(i).getName().equals(key)) {
                Toast.makeText(this, "User already added", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(key) &&
                            !(snapshot.child("username").getValue().toString().equals(user))) {

                        //Toast.makeText(Friends.this, friendArrayList.get(0).toString(), Toast.LENGTH_SHORT).show();

                        String id = reference.child(userkey).child("friendlist").push().getKey();
                        reference.child(userkey).child("friendlist").child(id).setValue(friend);
                        sent = 1;
                        break;
                    } else if(snapshot.child("username").getValue().toString().equals(key) &&
                            snapshot.child("username").getValue().toString().equals(user)) {

                        Toast.makeText(Friends.this, "Error! Can't add yourself", Toast.LENGTH_SHORT).show();
                        sent = 1;
                        break;
                    } else if(friendArrayList.contains(friend)) {

                        sent = 3;
                        break;
                    }
                }

                if(sent == 0) {
                    Toast.makeText(Friends.this, "User not found", Toast.LENGTH_SHORT).show();
                } else if(sent == 3) {
                    Toast.makeText(Friends.this, "Already friends", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void populateList() {
        dataHelper = new DataHelper3();
        friendArrayList = dataHelper.initializeData();
    }

    private void setKey(String key){
        this.userkey = key;
    }

}
