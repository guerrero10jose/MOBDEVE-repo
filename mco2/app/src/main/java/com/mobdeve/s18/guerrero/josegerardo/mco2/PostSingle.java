package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostSingle extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_single);

        ImageView user_pic, cap_pic;
        TextView username, task, likes, caption;

        user_pic = findViewById(R.id.prof_img);
        cap_pic = findViewById(R.id.cap_img);
        username = findViewById(R.id.prof_name);
        task = findViewById(R.id.task);
        likes = findViewById(R.id.cap_likes);
        caption = findViewById(R.id.caption);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            user_pic.setImageResource(extras.getInt("user_pic"));
            cap_pic.setImageResource(extras.getInt("cap_pic"));
            username.setText(extras.getString("username"));
            task.setText(extras.getString("task"));
            likes.setText(extras.getString("likes"));
            caption.setText(extras.getString("caption"));
        }
    }
}
