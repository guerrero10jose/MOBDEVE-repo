package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

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
        String TaskId = OldIntent.getStringExtra("TaskId");

        binding.etTask.setText(MainTask);
        binding.etTask.setFocusable(false);

        binding.btnSave.setOnClickListener(v -> {

            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(TaskId).child("subtasks");

            String key = reference.push().getKey();
            Subtask subtask = new Subtask(binding.etSubtask.getText().toString(), false, key);

            reference.child(key).setValue(subtask);

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });



    }

}
