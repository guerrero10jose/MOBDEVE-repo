package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

import java.util.ArrayList;

public class Subtask {

    private ArrayList<String> subtaskArrayList;


    public Subtask() {
        subtaskArrayList = new ArrayList<>();
    }

    public Subtask(ArrayList<String> subtaskArrayList) {
        this.subtaskArrayList = subtaskArrayList;
    }


    public ArrayList<String> getSubtaskArrayList() {
        return subtaskArrayList;
    }

    public void addSubtask(String subtask) {
        subtaskArrayList.add(subtask);
    }


    public void setSubtaskArrayList(ArrayList<String> subtaskArrayList) {
        this.subtaskArrayList = subtaskArrayList;
    }

    public int size() {
        return subtaskArrayList.size();
    }
}
