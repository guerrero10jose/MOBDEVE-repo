package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Friend;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Context context;
    private ArrayList<Friend> friendArrayList;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String key;

    public FriendAdapter(Context context, ArrayList<Friend> friendArrayList) {
        this.friendArrayList = friendArrayList;
        this.context = context;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
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

        holder.position = position;
        holder.friend = friendArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return friendArrayList.size();
    }

    protected class FriendViewHolder extends RecyclerView.ViewHolder {

        CircleImageView frnd_img;
        TextView frnd_name;
        int position;
        Friend friend;

        SessionManage sessionManage = new SessionManage(context);
        String user = sessionManage.getSession();

        public FriendViewHolder(View view) {
            super(view);

            frnd_img = view.findViewById(R.id.frnd_img);
            frnd_name = view.findViewById(R.id.frnd_name);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(context, "nice", Toast.LENGTH_SHORT).show();

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                if(snapshot.child("username").getValue().toString().equals(user)) {
                                    setKey(snapshot.getKey());

                                    //Toast.makeText(context, key, Toast.LENGTH_SHORT).show();

                                    for(DataSnapshot snapshot1 : snapshot.child("friendlist").getChildren()) {

                                        if(snapshot1.child("name").getValue().toString().equals(friend.getName())) {

                                            // ref.child(key).remove();

                                            reference.child(key).child("friendlist").child(snapshot1.getKey()).removeValue();
                                            break;
                                        }
                                    }

                                }
                            }

                            //Toast.makeText(context, "nope", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                    return true;
                }
            });

        }
    }

    private void setKey(String key) {
        this.key = key;
    }

}
