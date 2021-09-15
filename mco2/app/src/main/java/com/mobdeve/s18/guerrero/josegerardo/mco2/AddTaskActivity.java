package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityAddTaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;
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

        binding.btnDate.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        binding.btnTime.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        binding.btnSave.setOnClickListener(v -> {

            // to db
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("tasks");

            ArrayList<Subtask> subtasks = new ArrayList<>();



            // session
            SessionManage sessionManage = new SessionManage(getApplicationContext());

            String key = reference.push().getKey();

            Task task = new Task(binding.etTask.getText().toString(),
                    binding.etTag.getText().toString(),
                    subtasks, false,
                    binding.tvDate.getText().toString(),
                    binding.tvTime.getText().toString(),
                    key);

            reference.child(sessionManage.getSession()).child(key).setValue(task);

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
