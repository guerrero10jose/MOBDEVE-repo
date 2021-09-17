package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectFriendAdapter extends RecyclerView.Adapter<SelectFriendAdapter.SelectFriendViewHolder> {

    private Context context;
    private ArrayList<Friend> friendArrayList;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String key, note;

    public SelectFriendAdapter(Context context, ArrayList<Friend> friendArrayList, String note) {
        this.friendArrayList = friendArrayList;
        this.context = context;
        this.note = note;
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
    }

    @Override
    public SelectFriendAdapter.SelectFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        SelectFriendAdapter.SelectFriendViewHolder selectFriendViewHolder = new SelectFriendAdapter.SelectFriendViewHolder(view);

        return selectFriendViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectFriendViewHolder holder, int position) {
        holder.remove_btn.setVisibility(View.GONE);
        holder.frnd_name.setText(friendArrayList.get(position).getName());
        holder.frnd_img.setImageResource(friendArrayList.get(position).getUserimageid());


        holder.c_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = reference.orderByChild("username");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("username").getValue().toString().equals(friendArrayList.get(position).getName())) {

                                Log.v("here", "inside for loop if statement");
                                String id = reference.child(snapshot.getKey()).child("messages").push().getKey();
                                Log.v("here", "id = " + reference.child(snapshot.getKey()).child("messages"));
                                Log.v("here", "id = " + id);
                                Message message = new Message(holder.user, note, R.drawable.nopic, id);

                                reference.child(snapshot.getKey()).child("messages").child(id).setValue(message);
                                Toast.makeText(v.getContext(), "Note sent to " + friendArrayList.get(position).getName(), Toast.LENGTH_LONG).show();
                                ((Activity) v.getContext()).finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendArrayList.size();
    }

    private void setKey(String key) {
        this.key = key;
    }

    protected class SelectFriendViewHolder extends RecyclerView.ViewHolder {

        CircleImageView frnd_img;
        TextView frnd_name;
        ImageView remove_btn;
        ConstraintLayout c_layout;

        SessionManage sessionManage = new SessionManage(context);
        String user = sessionManage.getSession();

        public SelectFriendViewHolder(View view) {
            super(view);
            c_layout = view.findViewById(R.id.c_layout);
            frnd_img = view.findViewById(R.id.frnd_img);
            frnd_name = view.findViewById(R.id.frnd_name);
            remove_btn = view.findViewById(R.id.remove_btn);
        }
    }
}
