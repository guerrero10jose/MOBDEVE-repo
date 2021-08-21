package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityAddTaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Notes;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityAddTaskBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btn_date = findViewById(R.id.btn_date);
        Button btn_time = findViewById(R.id.btn_time);
        Button btn_save = findViewById(R.id.btn_save);

        btn_date.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        btn_time.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        btn_save.setOnClickListener(v -> {

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");

            Notes notes = new Notes();

            Task task = new Task(binding.etTask.getText().toString(),
                    binding.etTag.getText().toString(),
                    notes, false,
                    binding.tvDate.getText().toString(),
                    binding.tvTime.getText().toString());

            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            reference.child(sessionManage.getSession()).child(binding.etTask.getText().toString()).setValue(task);

            Intent intent = new Intent();
            intent.putExtra("task", binding.etTask.getText().toString());
            intent.putExtra("tag", binding.etTag.getText().toString());
            intent.putExtra("date", binding.tvDate.getText().toString());
            intent.putExtra("time", binding.tvTime.getText().toString());
            setResult(RESULT_OK, intent);
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
        TextView textView1 = findViewById(R.id.tv_date);
        textView1.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView2 = findViewById(R.id.tv_time);
        String temp = "";
        if(hourOfDay < 10) {
            temp += "0";
        }
        temp += hourOfDay + ":";
        if(minute < 10) {
            temp += "0";
        }
        temp += minute;
        textView2.setText(temp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
