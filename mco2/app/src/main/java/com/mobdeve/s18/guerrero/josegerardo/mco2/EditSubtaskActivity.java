package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityEditSubtaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;

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
        String MainTaskId = OldIntent.getStringExtra("TaskId");
        String OldSubtask = OldIntent.getStringExtra("Subtask");
        String SubtaskId = OldIntent.getStringExtra("SubtaskId");

        binding.etTask.setText(OldSubtask);

        //update this
        binding.btnSave.setOnClickListener(v -> {

            //Subtask subtask = new Subtask(binding.etTask.getText().toString(), false);

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");
            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            // edit
            reference.child(sessionManage.getSession()).child(MainTaskId).child("subtasks").child(SubtaskId).child("subtask").setValue(binding.etTask.getText().toString());

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            finish();
        });


        binding.btnDelete.setOnClickListener(v -> {
            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");
            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            //delete
            reference.child(sessionManage.getSession()).child(MainTaskId).child("subtasks").child(SubtaskId).removeValue();

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
