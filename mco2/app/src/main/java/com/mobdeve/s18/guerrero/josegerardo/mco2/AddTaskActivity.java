package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s18.guerrero.josegerardo.mco2.databinding.ActivityAddTaskBinding;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.ScheduleBroadcast;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Subtask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

            SimpleDateFormat getdateformat = new SimpleDateFormat("E, MMM dd yyyy HH:mm");
            Date dateval = null;
            try {
                dateval = getdateformat.parse(binding.tvDate.getText().toString() + " " + binding.tvTime.getText().toString());
                Log.v("here", "dateval = " + dateval.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int broadcastid = (int) System.currentTimeMillis();
            Log.v("here", "broadcastid = " + broadcastid);
            Task task = new Task(binding.etTask.getText().toString(),
                    binding.etTag.getText().toString(),
                    subtasks, false,
                    binding.tvDate.getText().toString(),
                    binding.tvTime.getText().toString(),
                    key, "Notes empty!", broadcastid, dateval.getTime());
            Log.v("here", "dateval = " + dateval.getTime());

            reference.child(sessionManage.getSession()).child(key).setValue(task);

            // set alarm
            createNotificationChannel();
            Intent intent = new Intent(this, ScheduleBroadcast.class);
            intent.putExtra("Task", binding.etTask.getText().toString());
            intent.putExtra("Tag", binding.etTag.getText().toString());
            intent.putExtra("BroadcastId", broadcastid);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, broadcastid, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            SimpleDateFormat ft1 = new SimpleDateFormat("E, MMM dd yyyy");
            SimpleDateFormat ft2 = new SimpleDateFormat("hh:mm");
            Date date1 = null;
            Date date2 = null;
            try {
                date1= ft1.parse(binding.tvDate.getText().toString());
                date2= ft2.parse(binding.tvTime.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(date1.getYear() + 1900, date1.getMonth(), date1.getDate(), date2.getHours(), date2.getMinutes(), 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            // close keyboard before ending activity
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            finish();
        });

    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel";
            String description = "Channel Sample";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MOBDEVE_FINAL", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
