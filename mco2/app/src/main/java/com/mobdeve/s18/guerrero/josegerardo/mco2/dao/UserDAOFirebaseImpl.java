package com.mobdeve.s18.guerrero.josegerardo.mco2.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.User;

import java.util.ArrayList;

public class UserDAOFirebaseImpl implements UserDAO{

    private String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(PATH);

    public UserDAOFirebaseImpl() {

    }

    public UserDAOFirebaseImpl(Context context) {

    }

    @Override
    public long addUser(User user) {
        final long[] result = {-1};
        myRef.push().setValue(user,
                new DatabaseReference.CompletionListener(){

                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if(error != null) {
                            Log.e("ERROR", "ERROR : " + error.getMessage());
                        }
                        else {
                            Log.d("SUCCESS", "DATA INSERTED");
                            result[0] = 1L;
                        }
                    }

                });
        return result[0];
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> result = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren()) {
                    User user = new User();
                    user.setUsername(data.child("username").getValue(String.class));
                    user.setEmail(data.child("email").getValue(String.class));
                    user.setPassword(data.child("password").getValue(String.class));
                    result.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return result;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(String username) {
        return 0;
    }

}
