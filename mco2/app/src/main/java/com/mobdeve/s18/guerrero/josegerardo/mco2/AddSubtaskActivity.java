package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityAddSubtaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;

public class AddSubtaskActivity extends AppCompatActivity {

    private ActivityAddSubtaskBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddSubtaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent OldIntent = getIntent();
        String MainTask = OldIntent.getStringExtra("Task");

        binding.etTask.setText(MainTask);
        binding.etTask.setFocusable(false);

        binding.btnSave.setOnClickListener(v -> {

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");
            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            Subtask subtask = new Subtask(binding.etSubtask.getText().toString(), false);
            reference.child(sessionManage.getSession()).child(binding.etTask.getText().toString()).child("subtasks").push().setValue(subtask);

        });



    }

}
