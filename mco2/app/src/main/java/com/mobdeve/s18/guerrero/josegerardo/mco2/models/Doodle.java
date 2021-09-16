package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Doodle {
    private String title, doodleid, doodleurl;

    public Doodle(String title, String doodleid, String doodleurl) {
        this.title = title;
        this.doodleid = doodleid;
        this.doodleurl = doodleurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoodleid() {
        return doodleid;
    }

    public void setDoodleid(String doodleid) {
        this.doodleid = doodleid;
    }

    public String getDoodleurl() {
        return doodleurl;
    }

    public void setDoodleurl(String doodleurl) {
        this.doodleurl = doodleurl;
    }
}
