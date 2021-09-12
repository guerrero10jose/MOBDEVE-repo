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

public class PostAdapter
        extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public PostAdapter(Context context, ArrayList<Post> postArrayList, RecyclerViewClickListener listener) {
        this.postArrayList = postArrayList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_datafeed, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.PostViewHolder holder, int position) {

        holder.prof_img.setImageResource(postArrayList.get(position).getUserImageId());
        //holder.cap_image.setImageResource(postArrayList.get(position).getImageId());
        holder.caption.setText(postArrayList.get(position).getCaption());
        holder.prof_name.setText(postArrayList.get(position).getUsername());
        holder.likes.setText(Integer.toString(postArrayList.get(position).getLikes()).concat(" LIKES"));
        holder.comments.setText(Integer.toString(postArrayList.get(position).getComments()).concat(" COMMENTS"));
        holder.task.setText(postArrayList.get(position).getTask());
        holder.btn_fave.setChecked(postArrayList.get(position).isLiked());

        Picasso.get().load(postArrayList.get(position).getModelname()).into(holder.cap_image);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    protected class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView prof_img;
        ImageView cap_image;
        TextView caption, prof_name, likes, comments, task;
        ToggleButton btn_fave;

        public PostViewHolder(View view) {
            super(view);

            prof_img = view.findViewById(R.id.prof_img);
            prof_name = view.findViewById(R.id.prof_name);
            task = view.findViewById(R.id.task);
            cap_image = view.findViewById(R.id.cap_img);
            caption = view.findViewById(R.id.caption);
            likes = view.findViewById(R.id.cap_likes);
            comments = view.findViewById(R.id.cap_comments);
            btn_fave = view.findViewById(R.id.btn_fave);

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
