package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String task, tag, time;
    private String date;
    private Notes notes;
    private boolean checked;

    public Task(String task, String tag, Notes notes, boolean checked, String date, String time) {
        this.task = task;
        this.tag = tag;
        this.notes = notes;
        this.checked = checked;
        this.date = date;
        this.time = time;
    }

    public String dateToStringShort () {
        SimpleDateFormat date = new SimpleDateFormat ("MMM d");

        return date.format(this.date);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
