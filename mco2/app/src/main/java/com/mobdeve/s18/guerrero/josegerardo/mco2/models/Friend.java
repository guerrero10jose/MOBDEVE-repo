package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Friend {

    public Friend(int userimageid, String name) {
        this.userimageid = userimageid;
        this.name = name;
    }

    public int getUserimageid() {
        return userimageid;
    }

    public String getName() {
        return name;
    }

    private int userimageid;
    private String name;

}
