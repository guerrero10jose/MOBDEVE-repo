package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;

public class IndivNotes extends AppCompatActivity {
    private Button btn_edit, btn_save;
    private TextView task;
    private EditText notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        btn_edit = findViewById(R.id.btn_edit);
        btn_save = findViewById(R.id.btn_ok);

        task = findViewById(R.id.task);
        notes = findViewById(R.id.notes);

        Intent OldIntent = getIntent();
        String MainTask = OldIntent.getStringExtra("Task");
        String TextNotes = OldIntent.getStringExtra("TextNotes");
        String TaskId = OldIntent.getStringExtra("TaskId");

        task.setText(MainTask);
        notes.setText(TextNotes);

        notes.setFocusable(false);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.setFocusableInTouchMode(true);
                notes.requestFocus();
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                notes.setSelection(notes.getText().length());
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // session
                SessionManage sessionManage = new SessionManage(getApplicationContext());

                // to db
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(TaskId).child("textnotes");

                reference.setValue(notes.getText().toString());

                notes.setFocusable(false);
            }
        });


    }
}
