package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.EditSubtaskActivity;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;

import java.util.ArrayList;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private ArrayList<Subtask> subtasks;
    private String task;
    private Context context;

    public SubtaskAdapter(ArrayList<Subtask> subtasks, String task, Context context) {
        this.subtasks = subtasks;
        this.task = task;
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
            intent.putExtra("Subtask", holder.tv_subtask.getText().toString());
            this.context.startActivity(intent);
            return false;
        });

        String subtask = this.subtasks.get(position).getSubtask();
        holder.tv_subtask.setText(subtask);
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    protected class SubtaskViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subtask;
        LinearLayout layout_edit;

        public SubtaskViewHolder(View itemView) {
            super(itemView);
            layout_edit = itemView.findViewById(R.id.layout_edit);
            tv_subtask = itemView.findViewById(R.id.tv_subtask);
        }

    }
}
