package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityEditSubtaskBinding;

public class EditSubtaskActivity extends AppCompatActivity{
    private ActivityEditSubtaskBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditSubtaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //update this
        binding.btnSave.setOnClickListener(v -> {

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");

            //Subtask subtask = new Subtask();

            // session
            //SessionManage sessionManage = new SessionManage(getApplicationContext());

            //reference.child(sessionManage.getSession()).child(binding.etTask.getText().toString()).setValue(task);

            Intent intent = new Intent(getApplicationContext(), MainView.class);
            startActivity(intent);
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
