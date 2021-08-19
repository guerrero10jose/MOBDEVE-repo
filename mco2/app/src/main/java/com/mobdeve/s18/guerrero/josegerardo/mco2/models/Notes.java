package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

import java.util.ArrayList;

public class Notes {

    private ArrayList<String> notesArrayList;


    public Notes() {
        notesArrayList = new ArrayList<>();
    }

    public Notes(ArrayList<String> notesArrayList) {
        this.notesArrayList = notesArrayList;
    }


    public ArrayList<String> getNotesArrayList() {
        return notesArrayList;
    }

    public void addNote(String note) {
        notesArrayList.add(note);
    }


    public void setNotesArrayList(ArrayList<String> notesArrayList) {
        this.notesArrayList = notesArrayList;
    }

    public int size() {
        return notesArrayList.size();
    }
}
