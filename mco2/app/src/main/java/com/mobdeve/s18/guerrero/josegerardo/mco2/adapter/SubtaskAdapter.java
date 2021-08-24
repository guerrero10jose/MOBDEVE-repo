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

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private Subtask subtask;
    private Context context;

    public SubtaskAdapter(Subtask subtask, Context context) {
        this.subtask = subtask;
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
            this.context.startActivity(intent);
            return false;
        });

        String subtask = this.subtask.getSubtaskArrayList().get(position);
        holder.tv_subtask.setText(subtask);
    }

    @Override
    public int getItemCount() {
        return subtask.size();
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
