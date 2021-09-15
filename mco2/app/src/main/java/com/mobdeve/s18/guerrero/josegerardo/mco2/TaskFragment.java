package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.TaskAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskFragment extends Fragment {

    private DataHelper dataHelper;
    private ArrayList<Task> taskArrayList;
    private TaskAdapter taskAdapter;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tasklist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        taskArrayList = new ArrayList<>();

        // session
        SessionManage sessionManage = new SessionManage(getContext());

        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tasks").child(sessionManage.getSession());
        Query taskQuery = reference.orderByChild("task");

        Log.v("here", "here here here");
        taskQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                taskArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ArrayList<Subtask> subtasks = new ArrayList<>();


                    if(snapshot.child("subtasks").exists())
                    {
                        Iterator<DataSnapshot> items = snapshot.child("subtasks").getChildren().iterator();;
                        while (items.hasNext()) {
                            DataSnapshot item = items.next();
                            String tempSubtask = item.child("subtask").getValue().toString();
                            Boolean tempChecked = Boolean.parseBoolean(item.child("checked").getValue().toString());
                            String tempSubtaskId = item.child("subtaskid").getValue().toString();
                            Subtask temp = new Subtask(tempSubtask, tempChecked, tempSubtaskId);


                            subtasks.add(temp);


                        }
                    }

                    for(int i = 0; i < subtasks.size(); i++)
                    {
                        Log.v("here", "subtask = " + subtasks.get(i).getSubtask());
                        Log.v("here", "tempChecked = " + subtasks.get(i).isChecked());
                        Log.v("here", "tempSubtaskId = " + subtasks.get(i).getSubtaskid());
                    }

                    Task task = new Task(snapshot.child("task").getValue().toString(),
                            snapshot.child("tag").getValue().toString(),
                            subtasks, Boolean.parseBoolean(snapshot.child("checked").getValue().toString()),
                            snapshot.child("date").getValue().toString(),
                            snapshot.child("time").getValue().toString(),
                            snapshot.child("taskid").getValue().toString(),
                            snapshot.child("textnotes").getValue().toString());

                    taskArrayList.add(task);
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//               // taskArrayList = new ArrayList<>();
//                taskArrayList.clear();
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    //Task task = snapshot.getValue(Task.class);
//
//                    ArrayList<Subtask> subtasks = new ArrayList<>();
//
//                    if(snapshot.child("subtasks").exists())
//                    {
//                        Iterator<DataSnapshot> items = snapshot.child("subtasks").getChildren().iterator();;
//                        while (items.hasNext()) {
//                            DataSnapshot item = items.next();
//                            String tempSubtask = item.child("subtask").getValue().toString();
//                            Boolean tempChecked = Boolean.parseBoolean(item.child("checked").getValue().toString());
//                            Subtask temp = new Subtask(tempSubtask, tempChecked);
//                            subtasks.add(temp);
//
//                        }
//                    }
//
//                    Task task = new Task(snapshot.child("task").getValue().toString(),
//                            snapshot.child("tag").getValue().toString(),
//                            subtasks, Boolean.parseBoolean(snapshot.child("checked").getValue().toString()),
//                            snapshot.child("date").getValue().toString(),
//                            snapshot.child("time").getValue().toString()
//                    );
//
//                    taskArrayList.add(task);
//                }
//                taskAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });

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

                    ArrayList<Subtask> subtasks = new ArrayList<>();

                    Task task = new Task(snapshot.child("task").getValue().toString(),
                            snapshot.child("tag").getValue().toString(),
                            subtasks, Boolean.parseBoolean(snapshot.child("checked").getValue().toString()),
                            snapshot.child("date").getValue().toString(),
                            snapshot.child("time").getValue().toString(),
                            snapshot.child("taskid").getValue().toString(), ""
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

        /*dataHelper = new DataHelper();
        taskArrayList = dataHelper.initializeTaskData();*/
    }
}