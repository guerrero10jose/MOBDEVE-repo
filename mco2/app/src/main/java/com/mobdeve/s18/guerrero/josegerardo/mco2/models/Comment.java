package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Comment {

    public Comment(String comment_name, String comment) {
        this.comment_name = comment_name;
        this.comment = comment;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment_name;
    private String comment;
}
