package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Doodle;

public class AddMenu extends AppCompatActivity implements View.OnClickListener {

    private CardView text_note_cv, doodle_cv, edit_cv, add_subtask_cv;

    private String Task, Tag, Date, Time, TaskId, TextNotes;
    private int BroadcastId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);



        text_note_cv = findViewById(R.id.text_note_cv);
        doodle_cv = findViewById(R.id.doodle_cv);
        edit_cv = findViewById(R.id.edit_cv);
        add_subtask_cv = findViewById(R.id.add_subtask_cv);

        text_note_cv.setOnClickListener(this);
        doodle_cv.setOnClickListener(this);
        edit_cv.setOnClickListener(this);
        add_subtask_cv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent oldIntent = getIntent();
        Intent intent;
        switch(v.getId()) {
            case R.id.text_note_cv:
                intent = new Intent(getApplicationContext(), IndivNotes.class);
                Task = oldIntent.getStringExtra("Task");
                TextNotes = oldIntent.getStringExtra("TextNotes");
                TaskId = oldIntent.getStringExtra("TaskId");
                intent.putExtra("Task", Task);
                intent.putExtra("TextNotes", TextNotes);
                intent.putExtra("TaskId", TaskId);
                startActivity(intent);
                break;
            case R.id.doodle_cv:
                intent = new Intent(getApplicationContext(), Doodles.class);
                TaskId = oldIntent.getStringExtra("TaskId");
                intent.putExtra("TaskId", TaskId);
                startActivity(intent);
                break;
            case R.id.edit_cv:
                intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                Task = oldIntent.getStringExtra("Task");
                Tag = oldIntent.getStringExtra("Tag");
                Date = oldIntent.getStringExtra("Date");
                Time = oldIntent.getStringExtra("Time");
                TaskId = oldIntent.getStringExtra("TaskId");
                BroadcastId = oldIntent.getIntExtra("BroadcastId", 0);
                intent.putExtra("Task", Task);
                intent.putExtra("Tag", Tag);
                intent.putExtra("Date", Date);
                intent.putExtra("Time", Time);
                intent.putExtra("TaskId", TaskId);
                intent.putExtra("BroadcastId", BroadcastId);
                startActivity(intent);
                break;
            case R.id.add_subtask_cv:
                intent = new Intent(getApplicationContext(), AddSubtaskActivity.class);
                Task = oldIntent.getStringExtra("Task");
                TaskId = oldIntent.getStringExtra("TaskId");
                intent.putExtra("Task", Task);
                intent.putExtra("TaskId", TaskId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
