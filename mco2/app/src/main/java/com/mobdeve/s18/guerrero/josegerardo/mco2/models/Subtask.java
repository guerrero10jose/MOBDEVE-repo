package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Subtask {

    private String Subtask;
    private boolean checked;

    public Subtask(String subtask, boolean checked) {
        Subtask = subtask;
        this.checked = checked;
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
}
