package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.EditSubtaskActivity;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder>{

        private ArrayList<Task> subtaskArrayList;
        private Context context;

        public SubtaskAdapter(ArrayList<Task> subtaskArrayList, Context context) {
            this.subtaskArrayList = subtaskArrayList;
            this.context = context;
        }

        @Override
        public SubtaskAdapter.SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_subtasks, parent, false);
            SubtaskAdapter.SubtaskViewHolder subtaskViewHolder = new SubtaskAdapter.SubtaskViewHolder(view);
            return subtaskViewHolder;
        }

        @Override
        public void onBindViewHolder(SubtaskAdapter.SubtaskViewHolder holder, int position) {
            //add
            holder.layout_edit.setOnLongClickListener(v -> {
                Intent intent = new Intent(context, EditSubtaskActivity.class);
                v.getContext().startActivity(intent);
                return false;
            });
        }

        @Override
        public int getItemCount() {
            return subtaskArrayList.size();
        }

        protected class SubtaskViewHolder extends RecyclerView.ViewHolder {
            LinearLayout layout_edit;

            public SubtaskViewHolder(View itemView) {
                super(itemView);
                layout_edit = itemView.findViewById(R.id.layout_edit);
            }

        }


}
