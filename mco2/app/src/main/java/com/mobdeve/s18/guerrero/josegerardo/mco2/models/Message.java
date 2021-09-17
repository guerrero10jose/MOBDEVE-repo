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

    public String getMessageId() {
        return messageId;
    }

    private String username, message, messageId;

    private int userImageId;

    public Message(String username, String message, int userImageId, String messageId) {
        this.username = username;
        this.message = message;
        this.userImageId = userImageId;
        this.messageId = messageId;
    }


}
