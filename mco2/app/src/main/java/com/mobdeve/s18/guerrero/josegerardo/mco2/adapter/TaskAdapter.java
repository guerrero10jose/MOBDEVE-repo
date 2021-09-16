package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.AddMenu;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskArrayList;
    private Context context;

    public TaskAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
        this.context = context;
    }

    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tasks, parent, false);

        TaskAdapter.TaskViewHolder taskViewHolder = new TaskAdapter.TaskViewHolder(view);

        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder holder, int position) {

        holder.tv_tag.setText(taskArrayList.get(position).getTag());
        holder.tv_task.setText(taskArrayList.get(position).getTask());

        String shortDate = "";
        String day = "";
        SimpleDateFormat ft1 = new SimpleDateFormat("E, MMM dd yyyy");
        SimpleDateFormat ft2 = new SimpleDateFormat("E");
        SimpleDateFormat ft3 = new SimpleDateFormat("MMM dd");

        try {
            Date date = ft1.parse(taskArrayList.get(position).getDate());
            day = ft2.format(date);
            shortDate = ft3.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tv_day.setText(day);
        holder.tv_date.setText(shortDate);
        holder.tv_time.setText(taskArrayList.get(position).getTime());
        //holder.tv_date.setText(taskArrayList.get(position).toString());

        holder.btn_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // session
                SessionManage sessionManage = new SessionManage(context);

                // to db
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(taskArrayList.get(position).getTaskid()).child("checked");

                // change value in db
                reference.setValue(isChecked);
            }
        });

        holder.btn_check.setChecked(taskArrayList.get(position).isChecked());

        holder.layout_edit.setOnLongClickListener(v -> {
            Intent intent = new Intent(this.context, AddMenu.class);
            intent.putExtra("Task", taskArrayList.get(position).getTask());
            intent.putExtra("Tag", taskArrayList.get(position).getTag());
            intent.putExtra("Date", taskArrayList.get(position).getDate());
            intent.putExtra("Time", taskArrayList.get(position).getTime());
            intent.putExtra("TaskId", taskArrayList.get(position).getTaskid());
            intent.putExtra("TextNotes", taskArrayList.get(position).getTextnotes());
            intent.putExtra("BroadcastId", taskArrayList.get(position).getBroadcastid());
            this.context.startActivity(intent);
            return false;
        });

        ArrayList<Subtask> subtasks = new ArrayList<>();
        for(int i = 0; i < taskArrayList.size(); i++) {
            if (position == i) {
                for (int j = 0; j < taskArrayList.get(position).getSubtasksArrayList().size(); j++) {
                    subtasks.add(taskArrayList.get(position).getSubtasksArrayList().get(j));
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        holder.rv_subtasklist.setLayoutManager(linearLayoutManager);

        SubtaskAdapter subtaskAdapter = new SubtaskAdapter(subtasks, taskArrayList.get(position).getTask(), taskArrayList.get(position).getTaskid(), holder.rv_subtasklist.getContext());
        holder.rv_subtasklist.setAdapter(subtaskAdapter);
    }


    @Override
    public int getItemCount() {
        return (taskArrayList == null) ? 0 : taskArrayList.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout_edit;
        TextView tv_tag, tv_task, tv_date, tv_time, tv_day;
        RecyclerView rv_subtasklist;
        ToggleButton btn_check;
        public TaskViewHolder(View itemView) {
            super(itemView);
            btn_check = itemView.findViewById(R.id.btn_check);
            layout_edit = itemView.findViewById(R.id.layout_edit);
            tv_tag = itemView.findViewById(R.id.tv_tag);
            tv_task = itemView.findViewById(R.id.tv_task);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            rv_subtasklist = itemView.findViewById(R.id.rv_subtasklist);
        }
    }
}


