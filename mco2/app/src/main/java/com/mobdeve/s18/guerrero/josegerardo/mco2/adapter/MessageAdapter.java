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
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private ArrayList<Message> messageArrayList;

    public MessageAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    public  interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_messages, parent, false);
        MessageAdapter.MessageViewHolder messageViewHolder = new MessageAdapter.MessageViewHolder(view);

        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageViewHolder holder, int position) {

        holder.send_img.setImageResource(messageArrayList.get(position).getUserImageId());
        holder.send_name.setText(messageArrayList.get(position).getUsername());
        holder.send_msg.setText(messageArrayList.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    protected class MessageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView send_img;
        TextView send_name, send_msg;

        public MessageViewHolder(View view) {
            super(view);

            send_img = view.findViewById(R.id.send_img);
            send_name = view.findViewById(R.id.send_name);
            send_msg = view.findViewById(R.id.send_msg);
        }
    }

}
