package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;

public class AddTaskAdapter extends RecyclerView.Adapter<AddTaskAdapter.AddTaskViewHolder> {

    private Context context;

    public AddTaskAdapter(Context context) {
        this.context = context;
    }
    @Override
    public AddTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_add_task, parent, false);
        AddTaskAdapter.AddTaskViewHolder addTaskViewHolder = new AddTaskAdapter.AddTaskViewHolder(view);

        return addTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(AddTaskAdapter.AddTaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class AddTaskViewHolder extends RecyclerView.ViewHolder{

        EditText et_task, et_tag;
        TextView tv_date, tv_time;
        public AddTaskViewHolder(View itemView) {
            super(itemView);
            et_task = itemView.findViewById(R.id.et_task);
            et_tag = itemView.findViewById(R.id.et_tag);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
