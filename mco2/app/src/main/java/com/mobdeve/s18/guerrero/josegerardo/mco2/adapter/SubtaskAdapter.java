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
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            holder.tv_task.setText(subtaskArrayList.get(position).getTask());

            String shortDate = "";
            SimpleDateFormat ft1 = new SimpleDateFormat("E, MMM dd yyyy");
            SimpleDateFormat ft2 = new SimpleDateFormat("MMM dd");

            try {
                Date date = ft1.parse(subtaskArrayList.get(position).getDate());
                shortDate = ft2.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.tv_date.setText(shortDate);
            holder.tv_time.setText(subtaskArrayList.get(position).getTime());

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
            TextView tv_task, tv_date, tv_time;
            LinearLayout layout_edit;

            public SubtaskViewHolder(View itemView) {
                super(itemView);
                layout_edit = itemView.findViewById(R.id.layout_edit);
                tv_task = itemView.findViewById(R.id.tv_task);
                tv_date = itemView.findViewById(R.id.tv_date);
                tv_time = itemView.findViewById(R.id.tv_time);
            }

        }


}
