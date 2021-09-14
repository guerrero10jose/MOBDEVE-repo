package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.LikedPost;
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
        //holder.btn_fave.setChecked(postArrayList.get(position).isLiked());
        holder.btn_fave.setChecked(false);

        FirebaseDatabase rootNode;
        DatabaseReference reference;

        // for like button
        //firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        SessionManage sessionManage = new SessionManage(context);

        String key = sessionManage.getSession();

        Picasso.get().load(postArrayList.get(position).getModelname()).into(holder.cap_image);

        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(key)) {
                        //Toast.makeText(context, snapshot.getKey(), Toast.LENGTH_SHORT).show();

                        //String id = reference.child(snapshot.getKey()).child("likedposts").push().getKey();

                        //LikedPost postL = new LikedPost("0", postArrayList.get(position).getPostid());

                        //likedposts.add(postArrayList.get(position).getPostid());

                        String kel = postArrayList.get(position).getPostid();

                        likedposts = new LikedPost(kel);
                        user_key = snapshot.getKey();

                        DatabaseReference ref2 = rootNode.getReference().child("users").child(user_key);

                        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.child("likedposts").exists()) {
                                    //Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
                                    Log.v("trulyy", snapshot.child("likedposts").getValue().toString());
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });


                        //reference.child(snapshot.getKey()).child("likedposts").child(id).setValue(postL);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        /*
        if(rootNode.getReference("users").child(user_key).child("likedposts") != null) {
            reference = rootNode.getReference("users").child(user_key).child("likedposts");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(postArrayList.get(position).getPostid().equals(snapshot.getValue().toString())) {
                            holder.btn_fave.setChecked(true);
                            break;
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

         */

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
        FirebaseDatabase rootNode;
        DatabaseReference reference;

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

            //firebase
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            /*
            btn_fave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(btn_fave.isChecked()) {

                    } else {

                        String id = reference.child(user_key).child("likedposts").push().getKey();

                        reference.child(user_key).child("likedposts").child(id).setValue(likedposts);
                    }
                }
            }); */


            /*
            //firebase
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            SessionManage sessionManage = new SessionManage(context);

            String key = sessionManage.getSession();

            btn_fave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(btn_fave.isChecked()) {
                        //Toast.makeText(context, "gay", Toast.LENGTH_SHORT).show();



                        //btn_fave.setChecked(false);
                    } else {
                        //Toast.makeText(context, "notgay", Toast.LENGTH_SHORT).show();
                        //btn_fave.setChecked(true);
                    }
                }
            }); */

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

    private static String user_key;
    private ArrayList<Post> postArrayList;
    private static LikedPost likedposts;
    private Context context;
    private RecyclerViewClickListener listener;
}
