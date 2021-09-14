package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private ArrayList<Comment> commentArrayList;

    public CommentAdapter(Context context, ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
        this.context = context;
    }

    public  interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        CommentAdapter.CommentViewHolder commentViewHolder = new CommentAdapter.CommentViewHolder(view);

        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, int position) {

        holder.comm_name.setText(commentArrayList.get(position).getComment_name());
        holder.comment.setText(commentArrayList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    protected class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView comm_name, comment;

        public CommentViewHolder(View view) {
            super(view);

            comm_name = view.findViewById(R.id.comm_name);
            comment = view.findViewById(R.id.comment);

        }
    }
}
