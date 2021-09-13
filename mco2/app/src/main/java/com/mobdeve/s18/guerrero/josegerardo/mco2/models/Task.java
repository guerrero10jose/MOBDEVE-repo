package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

import java.util.ArrayList;

public class Task {
    private String task, tag, time;
    private String date;
    private ArrayList<Subtask> subtasks;
    private boolean checked;

    public Task(String task, String tag, ArrayList<Subtask> subtasks, boolean checked, String date, String time) {
        this.task = task;
        this.tag = tag;
        this.subtasks = subtasks;
        this.checked = checked;
        this.date = date;
        this.time = time;
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

    public ArrayList<Subtask> getSubtasksArrayList() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
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
