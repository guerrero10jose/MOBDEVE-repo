package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.NotesAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.PostAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.TaskAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Notes;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DataHelper dataHelper;
    private ArrayList<Task> taskArrayList;
    private TaskAdapter taskAdapter;

    private ActivityResultLauncher<Intent> launchAddTask =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    SimpleDateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                    Date date = null;
                    try {
                        date = format.parse(data.getStringExtra("date"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Notes notes = new Notes();
                    Task task = new Task(
                            data.getStringExtra("task"),
                            data.getStringExtra("tag"),
                            notes, false, date, data.getStringExtra("time")
                    );
                    taskAdapter.addTask(task);
                }
            });

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tasklist);

        AppCompatButton btn_add_task;
        btn_add_task = view.findViewById(R.id.btn_add_task);

        btn_add_task.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            launchAddTask.launch(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        populateList();

        taskAdapter = new TaskAdapter(this.getContext(), taskArrayList);
        recyclerView.setAdapter(taskAdapter);

        return view;
    }

    private void populateList() {
        dataHelper = new DataHelper();
        taskArrayList = dataHelper.initializeTaskData();
    }
}