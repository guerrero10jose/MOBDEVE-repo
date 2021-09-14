package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPostAdapter
        extends RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder> {

    public UserPostAdapter(Context context, ArrayList<Post> postArrayList, RecyclerViewClickListener listener) {
        this.postArrayList = postArrayList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public UserPostAdapter.UserPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_indivposts, parent, false);
        UserPostViewHolder postViewHolder = new UserPostViewHolder(view);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(UserPostAdapter.UserPostViewHolder holder, int position) {

        //holder.cap_img.setImageResource(postArrayList.get(position).getUserImageId());
        holder.task.setText(postArrayList.get(position).getTask());
        Picasso.get().load(postArrayList.get(position).getModelname()).resize(50, 50).centerCrop().into(holder.cap_img);

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    protected class UserPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView cap_img;
        TextView task;

        public UserPostViewHolder(View view) {
            super(view);

            task = view.findViewById(R.id.post_task);
            cap_img = view.findViewById(R.id.post_img);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public  interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    private ArrayList<Post> postArrayList;
    private Context context;
    private RecyclerViewClickListener listener;
}
