package com.mobdeve.s18.guerrero.josegerardo.mco2.models;

public class Message {
    
    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public int getUserImageId() {
        return userImageId;
    }

    private String username, message;
    private int userImageId;

    public Message(String username, String message, int userImageId) {
        this.username = username;
        this.message = message;
        this.userImageId = userImageId;
    }


}
