package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.TaskAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Notes;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskFragment extends Fragment {

    private DataHelper dataHelper;
    private ArrayList<Task> taskArrayList;
    private TaskAdapter taskAdapter;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

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
                            notes, false, date.toString(), data.getStringExtra("time")
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

        Button btn_add_task;
        btn_add_task = view.findViewById(R.id.btn_add_task);

        btn_add_task.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            launchAddTask.launch(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        taskArrayList = new ArrayList<>();

        // session
        SessionManage sessionManage = new SessionManage(getContext());

        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tasks").child(sessionManage.getSession());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // taskArrayList = new ArrayList<>();
                taskArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Task task = snapshot.getValue(Task.class);

                    Notes notes = new Notes();

                    Task task = new Task(snapshot.child("task").getValue().toString(),
                            snapshot.child("tag").getValue().toString(),
                            notes, Boolean.parseBoolean(snapshot.child("checked").getValue().toString()),
                            snapshot.child("date").getValue().toString(),
                            snapshot.child("time").getValue().toString()
                    );

                    taskArrayList.add(task);

                    //Log.v("here", Integer.toString(taskArrayList.size()));

                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        taskAdapter = new TaskAdapter(this.getContext(), taskArrayList);
        recyclerView.setAdapter(taskAdapter);

        return view;
    }

    private void populateList() {

        // session
        SessionManage sessionManage = new SessionManage(getContext());

        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tasks").child(sessionManage.getSession());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                taskArrayList = new ArrayList<>();
                taskArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Task task = snapshot.getValue(Task.class);

                    Notes notes = new Notes();

                    Task task = new Task(snapshot.child("task").getValue().toString(),
                            snapshot.child("tag").getValue().toString(),
                            notes, Boolean.parseBoolean(snapshot.child("checked").getValue().toString()),
                            snapshot.child("date").getValue().toString(),
                            snapshot.child("time").getValue().toString()
                            );

                    taskArrayList.add(task);

                    Log.v("here", Integer.toString(taskArrayList.size()));

                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        /*dataHelper = new DataHelper();
        taskArrayList = dataHelper.initializeTaskData();*/
    }
}