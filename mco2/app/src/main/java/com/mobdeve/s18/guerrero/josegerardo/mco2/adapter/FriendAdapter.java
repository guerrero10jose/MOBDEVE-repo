package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Context context;
    private ArrayList<Friend> friendArrayList;

    public FriendAdapter(Context context, ArrayList<Friend> friendArrayList) {
        this.friendArrayList = friendArrayList;
        this.context = context;
    }

    public  interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        FriendViewHolder friendViewHolder = new FriendViewHolder(view);

        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {

        holder.frnd_img.setImageResource(friendArrayList.get(position).getUserimageid());
        holder.frnd_name.setText(friendArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return friendArrayList.size();
    }

    protected class FriendViewHolder extends RecyclerView.ViewHolder {

        CircleImageView frnd_img;
        TextView frnd_name;

        public FriendViewHolder(View view) {
            super(view);

            frnd_img = view.findViewById(R.id.frnd_img);
            frnd_name = view.findViewById(R.id.frnd_name);

        }
    }

}
