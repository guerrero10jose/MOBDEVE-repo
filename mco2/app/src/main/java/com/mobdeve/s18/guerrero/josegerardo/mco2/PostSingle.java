package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.CommentAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostSingle extends AppCompatActivity {

    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private int com_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_single);

        ImageView user_pic, cap_pic;
        TextView username, task, likes, caption;
        Button btn_comment;
        EditText comment;

        user_pic = findViewById(R.id.prof_img);
        cap_pic = findViewById(R.id.cap_img);
        username = findViewById(R.id.prof_name);
        task = findViewById(R.id.task);
        likes = findViewById(R.id.cap_likes);
        caption = findViewById(R.id.caption);
        btn_comment = findViewById(R.id.btn_comment);
        comment = findViewById(R.id.user_comment);


        recyclerView = findViewById(R.id.rv_commentlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        Bundle extras = getIntent().getExtras();

        // get comments
        commentArrayList = new ArrayList<>();
        populateList(extras);

        //commentArrayList = new ArrayList<>();
        //commentArrayList.add(new Comment("Wow", "hello"));

        commentAdapter = new CommentAdapter(getApplicationContext(), commentArrayList);
        recyclerView.setAdapter(commentAdapter);


        if(extras != null) {
            user_pic.setImageResource(extras.getInt("user_pic"));
            //cap_pic.setImageResource(extras.getInt("cap_pic"));
            username.setText(extras.getString("username"));
            task.setText(extras.getString("task"));
            likes.setText(extras.getString("likes"));
            caption.setText(extras.getString("caption"));

            com_count = extras.getInt("com_count");

            Picasso.get().load(extras.getString("cap_pic")).into(cap_pic);
        }

        // comment button
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comment.getText().toString().length() < 1) {
                    Toast.makeText(PostSingle.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
                } else {
                    String key = extras.getString("postid");

                    // firebase
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("posts");

                    SessionManage sessionManage = new SessionManage(getApplicationContext());

                    Comment commentvar = new Comment(sessionManage.getSession(), comment.getText().toString());

                    String id = reference.push().getKey();

                    //int com_num = Integer.parseInt(extras.getString("comments")) + 1;

                    reference.child(key).child("commentlist").child(id).setValue(commentvar);
                    reference.child(key).child("comments").setValue(com_count + 1);

                    com_count += 1;
                }
            }
        });
    }

    private void populateList(Bundle extras) {

        // session
        SessionManage sessionManage = new SessionManage(getApplicationContext());

        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                commentArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //Toast.makeText(PostSingle.this, snapshot.child("postid").getValue().toString(), Toast.LENGTH_SHORT).show();
                    //Log.v("yo", snapshot.child("postid").getValue().toString());

                    if(snapshot.child("postid").getValue().toString().equals(extras.getString("postid"))) {

                        for(DataSnapshot snapshot1 : snapshot.child("commentlist").getChildren()) {
                            //Log.v("yes", snapshot1.child("comment_name").getValue().toString());

                            Comment commentvar = new Comment(snapshot1.child("comment_name").getValue().toString(), snapshot1.child("comment").getValue().toString());

                            commentArrayList.add(commentvar);
                        }
                        commentAdapter.notifyDataSetChanged();
                    }
                }
                        /*
                    if(snapshot.child("postid").equals(extras.getString("postid")) &&
                        snapshot.child("commentlist").exists())  {

                        Comment commentvar = new Comment(snapshot.child("commentlist").child("comment_name").getValue().toString(),
                                snapshot.child("commentlist").child("comment").getValue().toString());

                        commentArrayList.add(commentvar);
                    }

                    //commentAdapter.notifyDataSetChanged();
                } */
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
