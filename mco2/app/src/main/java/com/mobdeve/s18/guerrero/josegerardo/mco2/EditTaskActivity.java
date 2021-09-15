package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityEditTaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private ActivityEditTaskBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent OldIntent = getIntent();
        String OldTask, OldTag, OldDate, OldTime, TaskId;;
        OldTask = OldIntent.getStringExtra("Task");
        OldTag = OldIntent.getStringExtra("Tag");
        OldDate = OldIntent.getStringExtra("Date");
        OldTime = OldIntent.getStringExtra("Time");
        TaskId = OldIntent.getStringExtra("TaskId");

        binding.etTask.setText(OldTask);
        binding.etTag.setText(OldTag);
        binding.tvDate.setText(OldDate);
        binding.tvTime.setText(OldTime);


        binding.btnDate.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        binding.btnTime.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        //update this
        binding.btnSave.setOnClickListener(v -> {

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");

            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            // edit
            reference.child(sessionManage.getSession()).child(TaskId).child("task").setValue(binding.etTask.getText().toString());
            reference.child(sessionManage.getSession()).child(TaskId).child("tag").setValue(binding.etTag.getText().toString());
            reference.child(sessionManage.getSession()).child(TaskId).child("date").setValue(binding.tvDate.getText().toString());
            reference.child(sessionManage.getSession()).child(TaskId).child("time").setValue(binding.tvTime.getText().toString());

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnDelete.setOnClickListener(v -> {
            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");

            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            // delete
            reference.child(sessionManage.getSession()).child(TaskId).removeValue();

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //   String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        SimpleDateFormat ft =
                new SimpleDateFormat ("E, MMM dd yyyy");
        //   String currentDateString = c.getTime().toString();
        String currentDateString = ft.format(c.getTime());
        //  Toast.makeText(getApplicationContext(), currentDateString, Toast.LENGTH_LONG).show();
        binding.tvDate.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String temp = "";
        if(hourOfDay < 10) {
            temp += "0";
        }
        temp += hourOfDay + ":";
        if(minute < 10) {
            temp += "0";
        }
        temp += minute;
        binding.tvTime.setText(temp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
