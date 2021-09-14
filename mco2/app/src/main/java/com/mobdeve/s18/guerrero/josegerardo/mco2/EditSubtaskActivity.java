package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityEditSubtaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;

import java.util.Iterator;

public class EditSubtaskActivity extends AppCompatActivity{
    private ActivityEditSubtaskBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditSubtaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent OldIntent = getIntent();
        String MainTask = OldIntent.getStringExtra("Task");
        String OldSubtask = OldIntent.getStringExtra("Subtask");

        binding.etTask.setText(OldSubtask);

        //update this
        binding.btnSave.setOnClickListener(v -> {

            Subtask subtask = new Subtask(binding.etTask.getText().toString(), false);

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");
            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());
            // query to look for old subtask name in db
            Query ref = reference.child(sessionManage.getSession()).child(MainTask).child("subtasks").orderByChild("subtask").equalTo(OldSubtask);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Iterator<DataSnapshot> items = snapshot.getChildren().iterator();

                    //first item to match the old subtask name
                    DataSnapshot item = items.next();
                    item.getRef().setValue(subtask);

                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            finish();
        });


        binding.btnDelete.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
