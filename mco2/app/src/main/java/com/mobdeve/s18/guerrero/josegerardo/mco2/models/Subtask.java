package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Subtask {

    private String Subtask, subtaskid;
    private boolean checked;



    public Subtask(String subtask, boolean checked, String subtaskid) {
        Subtask = subtask;
        this.checked = checked;
        this.subtaskid = subtaskid;
    }

    public String getSubtask() {
        return Subtask;
    }

    public void setSubtask(String subtask) {
        Subtask = subtask;
        checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(String subtaskid) {
        this.subtaskid = subtaskid;
    }
}
