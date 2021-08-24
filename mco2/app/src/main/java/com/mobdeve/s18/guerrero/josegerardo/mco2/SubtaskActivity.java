package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.SubtaskAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivitySubtaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;

public class SubtaskActivity extends AppCompatActivity {
    private ActivitySubtaskBinding binding;
    private DataHelper dataHelper = new DataHelper();
    private ArrayList<Task> subtaskArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubtaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subtaskArrayList = dataHelper.initializeTaskData();

        SubtaskAdapter subtaskAdapter = new SubtaskAdapter(subtaskArrayList, getApplicationContext());

        binding.rvSubtasklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvSubtasklist.setAdapter(subtaskAdapter);
    }
}
