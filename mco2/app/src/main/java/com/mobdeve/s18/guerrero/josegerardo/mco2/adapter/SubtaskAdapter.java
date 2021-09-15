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

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.EditSubtaskActivity;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;

import java.util.ArrayList;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private ArrayList<Subtask> subtasks;
    private String task, taskid;
    private Context context;

    public SubtaskAdapter(ArrayList<Subtask> subtasks, String task, String taskid, Context context) {
        this.subtasks = subtasks;
        this.task = task;
        this.taskid = taskid;
        this.context = context;
    }

    @Override
    public SubtaskAdapter.SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtasks, parent, false);
        SubtaskAdapter.SubtaskViewHolder subtaskViewHolder = new SubtaskAdapter.SubtaskViewHolder(view);
        return subtaskViewHolder;
    }

    @Override
    public void onBindViewHolder(SubtaskAdapter.SubtaskViewHolder holder, int position) {

        holder.layout_edit.setOnLongClickListener(v -> {
            Intent intent = new Intent(this.context, EditSubtaskActivity.class);
            intent.putExtra("Task", task);
            intent.putExtra("TaskId", taskid);
            intent.putExtra("Subtask", holder.tv_subtask.getText().toString());
            intent.putExtra("SubtaskId", subtasks.get(position).getSubtaskid());
            this.context.startActivity(intent);
            return false;
        });

        holder.btn_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // session
                SessionManage sessionManage = new SessionManage(context);

                // to db
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(taskid).child("subtasks").child(subtasks.get(position).getSubtaskid()).child("checked");

                // change value in db
                reference.setValue(isChecked);
            }
        });
        String subtask = this.subtasks.get(position).getSubtask();
        holder.btn_check.setChecked(subtasks.get(position).isChecked());
        holder.tv_subtask.setText(subtask);
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    protected class SubtaskViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subtask;
        LinearLayout layout_edit;
        ToggleButton btn_check;

        public SubtaskViewHolder(View itemView) {
            super(itemView);
            layout_edit = itemView.findViewById(R.id.layout_edit);
            tv_subtask = itemView.findViewById(R.id.tv_subtask);
            btn_check = itemView.findViewById(R.id.btn_check);
        }

    }
}
