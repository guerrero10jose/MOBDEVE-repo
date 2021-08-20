package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Notes;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskArrayList;
    private Context context;


    public TaskAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
        this.context = context;
    }

    public void addTasks(ArrayList<Task> taskArrayList) {
        this.taskArrayList.clear();
        this.taskArrayList.addAll(taskArrayList);
        notifyDataSetChanged();
    }

    public void addTask(Task task) {
        taskArrayList.add(0, task);
        notifyItemInserted(0);
        notifyDataSetChanged();
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

        holder.tv_task.setText(taskArrayList.get(position).getTask());
        holder.tv_date.setText(taskArrayList.get(position).dateToStringShort());

        Notes notesArrayList = new Notes();
        for(int i = 0; i < taskArrayList.size(); i++) {
            if (position == i && position < taskArrayList.get(position).getNotes().size()) {
                for (int j = 0; j < taskArrayList.get(position).getNotes().size(); j++) {
                    notesArrayList.addNote(taskArrayList.get(position).getNotes().getNotesArrayList().get(j));
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        holder.rv_noteslist.setLayoutManager(linearLayoutManager);

        NotesAdapter notesAdapter = new NotesAdapter(notesArrayList, holder.rv_noteslist.getContext());
        holder.rv_noteslist.setAdapter(notesAdapter);
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tv_task, tv_date;
        RecyclerView rv_noteslist;
        public TaskViewHolder(View itemView) {
            super(itemView);
            tv_task = itemView.findViewById(R.id.tv_task);
            tv_date = itemView.findViewById(R.id.tv_date);
            rv_noteslist = itemView.findViewById(R.id.rv_noteslist);
        }
    }
}


