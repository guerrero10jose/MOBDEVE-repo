package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private CardView messages, post, friends, notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        messages = findViewById(R.id.msg_cv);
        post = findViewById(R.id.pst_cv);
        friends = findViewById(R.id.frnd_cv);
        notes = findViewById(R.id.notes_cv);

        messages.setOnClickListener(this);
        post.setOnClickListener(this);
        friends.setOnClickListener(this);
        notes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()) {
            case R.id.msg_cv:
                intent = new Intent(getApplicationContext(), Messages.class);
                startActivity(intent);
                break;
            case R.id.pst_cv:
                intent = new Intent(getApplicationContext(), CreatePost.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
